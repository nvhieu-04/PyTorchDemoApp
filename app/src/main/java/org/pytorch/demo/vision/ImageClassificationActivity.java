package org.pytorch.demo.vision;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.SystemClock;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.camera.core.ImageProxy;

import org.pytorch.IValue;
import org.pytorch.Module;
import org.pytorch.Tensor;
import org.pytorch.demo.Constants;
import org.pytorch.demo.R;
import org.pytorch.demo.Utils;
import org.pytorch.demo.ui.plant.AddInformation;
import org.pytorch.demo.vision.view.ResultRowView;
import org.pytorch.torchvision.TensorImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;
import java.util.Queue;

public class ImageClassificationActivity extends AbstractCameraXActivity<ImageClassificationActivity.AnalysisResult> {

  public static final String INTENT_MODULE_ASSET_NAME = "INTENT_MODULE_ASSET_NAME";
  public static final String INTENT_INFO_VIEW_TYPE = "INTENT_INFO_VIEW_TYPE";

  private static final int INPUT_TENSOR_WIDTH = 224;
  private static final int INPUT_TENSOR_HEIGHT = 224;
  private static final int TOP_K = 1;
  private static final int MOVING_AVG_PERIOD = 10;
  private static final String FORMAT_MS = "%dms";
  private static final String FORMAT_AVG_MS = "avg:%.0fms";

  private static final String FORMAT_FPS = "%.1fFPS";
  public static final String SCORES_FORMAT = "%.2f%%";

  static class AnalysisResult {

    private final String[] topNClassNames;
    private final float[] topNScores;
    private final long analysisDuration;
    private final long moduleForwardDuration;

    public AnalysisResult(String[] topNClassNames, float[] topNScores,
                          long moduleForwardDuration, long analysisDuration) {
      this.topNClassNames = topNClassNames;
      this.topNScores = topNScores;
      this.moduleForwardDuration = moduleForwardDuration;
      this.analysisDuration = analysisDuration;
    }
  }

  private boolean mAnalyzeImageErrorState;
  private ResultRowView[] mResultRowViews = new ResultRowView[TOP_K];
  private TextView mFpsText;
  private TextView mMsText;
  private TextView mMsAvgText;
  private Module mModule;
  private String mModuleAssetName;
  private FloatBuffer mInputTensorBuffer;
  private Tensor mInputTensor;
  private long mMovingAvgSum = 0;
  private Queue<Long> mMovingAvgQueue = new LinkedList<>();
  private ImageView mCaptureImage;
  protected String mDiseaseName;
  protected Image imageCapture;
  protected String fname;
  protected TextView ram,cpu, gpu;

  protected float resultTopK;
  @Override
  protected int getContentViewLayoutId() {
    return R.layout.activity_image_classification;
  }

  @Override
  protected TextureView getCameraPreviewTextureView() {
    return ((ViewStub) findViewById(R.id.image_classification_texture_view_stub))
        .inflate()
        .findViewById(R.id.image_classification_texture_view);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final ResultRowView headerResultRowView =
        findViewById(R.id.image_classification_result_header_row);
    headerResultRowView.nameTextView.setText(R.string.image_classification_results_header_row_name);
    headerResultRowView.scoreTextView.setText(R.string.image_classification_results_header_row_score);

    mResultRowViews[0] = findViewById(R.id.image_classification_top1_result_row);
//    mResultRowViews[1] = findViewById(R.id.image_classification_top2_result_row);
//    mResultRowViews[2] = findViewById(R.id.image_classification_top3_result_row);
    Intent intent = getIntent();
    String nameRoom = intent.getStringExtra("nameRoom");
    mFpsText = findViewById(R.id.image_classification_fps_text);
    mMsText = findViewById(R.id.image_classification_ms_text);
    mMsAvgText = findViewById(R.id.image_classification_ms_avg_text);
    mCaptureImage = findViewById(R.id.imageView12);
    ram = findViewById(R.id.ramUsage);
    cpu = findViewById(R.id.cpuUsage);
    gpu = findViewById(R.id.gpuUsage);
    ram.setVisibility(View.VISIBLE);
//    cpu.setVisibility(View.VISIBLE);
    mCaptureImage.setOnClickListener(v -> {
        Intent intent1 = new Intent(ImageClassificationActivity.this, AddInformation.class);
        intent1.putExtra("diseaseName", mDiseaseName);
        intent1.putExtra("nameRoom", nameRoom);
        intent1.putExtra("image", fname);
        startActivity(intent1);
    });
    mResultRowViews[0].setOnClickListener(v -> {
      Dialog dialog = new Dialog(this);
      dialog.setContentView(R.layout.dialog_custom_detail_disease);
      TextView nameDisease = dialog.findViewById(R.id.textView7);
      TextView description = dialog.findViewById(R.id.textView13);
      TextView copyRight = dialog.findViewById(R.id.textView18);
      ImageView imageDisease = dialog.findViewById(R.id.imageView11);
      copyRight.setText("Nguồn: \"Một số sâu bệnh hại trên cây ngô và biện pháp phòng trừ,\" HPSTIC, 2019. [Online]. Available: http://hpstic.vn:96/tin-chi-tiet/Mot-so-sau-benh-hai-tren-cay-ngo-va-bien-phap-phong-tru-1387.html. [Accessed: Jun. 22, 2023].");
      nameDisease.setText(mDiseaseName);
      dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
      dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
      if(Objects.equals(mDiseaseName, "Bệnh đốm lá xám"))
      {
        Toast.makeText(getApplicationContext(), mDiseaseName, Toast.LENGTH_SHORT).show();
        description.setText(Html.fromHtml(Constants.dom_la_xam));
        imageDisease.setImageResource(R.drawable.benhdomlanho);
        dialog.show();
      }
      else if (Objects.equals(mDiseaseName, "Bệnh gỉ sắt"))
      {
        Toast.makeText(getApplicationContext(), mDiseaseName, Toast.LENGTH_SHORT).show();
        description.setText(Html.fromHtml(Constants.common_rust));
        imageDisease.setImageResource(R.drawable.gisat);
        dialog.show();
      }
      else if (Objects.equals(mDiseaseName, "Bệnh cháy lá"))
      {
        Toast.makeText(getApplicationContext(), mDiseaseName, Toast.LENGTH_SHORT).show();
        description.setText(Html.fromHtml(Constants.chay_la));
        imageDisease.setImageResource(R.drawable.chayla);
        dialog.show();
      }
      else {
        Toast.makeText(getApplicationContext(), "Không có thông tin về bệnh này", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
      }
    });
  }

  @Override
  protected void applyToUiAnalyzeImageResult(AnalysisResult result) {
    mMovingAvgSum += result.moduleForwardDuration;
    mMovingAvgQueue.add(result.moduleForwardDuration);
    if (mMovingAvgQueue.size() > MOVING_AVG_PERIOD) {
      mMovingAvgSum -= mMovingAvgQueue.remove();
    }

    for (int i = 0; i < TOP_K; i++) {
      final ResultRowView rowView = mResultRowViews[i];
      rowView.nameTextView.setText(result.topNClassNames[i]);

      if (result.topNScores[i] * 75 >= 100)
      {
        rowView.scoreTextView.setText("100%");
      }
      else
      {
        rowView.scoreTextView.setText(String.format(Locale.US, SCORES_FORMAT,
                (result.topNScores[i]) * 75 ));
      }

      rowView.setProgressState(false);
      ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
      ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
      activityManager.getMemoryInfo(memoryInfo);
      long totalMemoryDevice = memoryInfo.totalMem;
      Runtime runtime = Runtime.getRuntime();
      long totalMemory = runtime.totalMemory();
      DecimalFormat df = new DecimalFormat("#.##");
      ram.setText("Ram Usage: " + totalMemory/1024/100 + "MB" + " / " + totalMemoryDevice/1024/100 + "MB");
    }

    mMsText.setText(String.format(Locale.US, FORMAT_MS, result.moduleForwardDuration));
    if (mMsText.getVisibility() != View.VISIBLE) {
      mMsText.setVisibility(View.VISIBLE);
    }
    mFpsText.setText(String.format(Locale.US, FORMAT_FPS, (1000.f / result.analysisDuration)));
    if (mFpsText.getVisibility() != View.VISIBLE) {
      mFpsText.setVisibility(View.VISIBLE);
    }

    if (mMovingAvgQueue.size() == MOVING_AVG_PERIOD) {
      float avgMs = (float) mMovingAvgSum / MOVING_AVG_PERIOD;
      mMsAvgText.setText(String.format(Locale.US, FORMAT_AVG_MS, avgMs));
      if (mMsAvgText.getVisibility() != View.VISIBLE) {
        mMsAvgText.setVisibility(View.VISIBLE);
      }
    }
  }
  private Bitmap toBitmap(Image image) {
    Image.Plane[] planes = image.getPlanes();
    ByteBuffer yBuffer = planes[0].getBuffer();
    ByteBuffer uBuffer = planes[1].getBuffer();
    ByteBuffer vBuffer = planes[2].getBuffer();

    int ySize = yBuffer.remaining();
    int uSize = uBuffer.remaining();
    int vSize = vBuffer.remaining();

    byte[] nv21 = new byte[ySize + uSize + vSize];
    //U and V are swapped
    yBuffer.get(nv21, 0, ySize);
    vBuffer.get(nv21, ySize, vSize);
    uBuffer.get(nv21, ySize + vSize, uSize);

    YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 90, out);

    byte[] imageBytes = out.toByteArray();
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
  }
  protected void saveImage(Bitmap finalBitmap) {
//    File myDir = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),);
//    myDir.mkdirs();
    fname = "Image-Result" + ".jpg";
    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fname);
    if (file.exists()) file.delete();
    try {
      FileOutputStream out = new FileOutputStream(file);
      finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
      out.flush();
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  protected String getModuleAssetName() {
    if (!TextUtils.isEmpty(mModuleAssetName)) {
      return mModuleAssetName;
    }
//    File sample = getIntent().getStringExtra(INTENT_MODULE_ASSET_NAME);
    final String moduleAssetNameFromIntent = getIntent().getStringExtra(INTENT_MODULE_ASSET_NAME);
    mModuleAssetName = !TextUtils.isEmpty(moduleAssetNameFromIntent)
        ? moduleAssetNameFromIntent
        : "";

    return mModuleAssetName;
  }

  @Override
  protected String getInfoViewAdditionalText() {
    return getModuleAssetName();
  }
  @Override
  @WorkerThread
  @Nullable
  protected AnalysisResult analyzeImage(ImageProxy image, int rotationDegrees) {
    if (mAnalyzeImageErrorState) {
      return null;
    }
    try {
      if (mModule == null) {
        final String moduleFileAbsoluteFilePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), getModuleAssetName()).getAbsolutePath();
        mModule = Module.load(moduleFileAbsoluteFilePath);
        mInputTensorBuffer =
            Tensor.allocateFloatBuffer(3 * INPUT_TENSOR_WIDTH * INPUT_TENSOR_HEIGHT);
        mInputTensor = Tensor.fromBlob(mInputTensorBuffer, new long[]{1, 3, INPUT_TENSOR_HEIGHT, INPUT_TENSOR_WIDTH});
      }
      final long startTime = SystemClock.elapsedRealtime();
      TensorImageUtils.imageYUV420CenterCropToFloatBuffer(
              Objects.requireNonNull(image.getImage()), rotationDegrees,
          INPUT_TENSOR_WIDTH, INPUT_TENSOR_HEIGHT,
          TensorImageUtils.TORCHVISION_NORM_MEAN_RGB,
          TensorImageUtils.TORCHVISION_NORM_STD_RGB,
          mInputTensorBuffer, 0);
      imageCapture = image.getImage();
      Bitmap bitmap = toBitmap(imageCapture);
      saveImage(bitmap);
      final long moduleForwardStartTime = SystemClock.elapsedRealtime();
      final Tensor outputTensor = mModule.forward(IValue.from(mInputTensor)).toTensor();
      final long moduleForwardDuration = SystemClock.elapsedRealtime() - moduleForwardStartTime;

      final float[] scores = outputTensor.getDataAsFloatArray();
      final int[] ixs = Utils.topK(scores, TOP_K);
      final String[] topKClassNames = new String[TOP_K];
      final float[] topKScores = new float[TOP_K];
      for (int i = 0; i < TOP_K; i++) {
        final int ix = ixs[i];
        topKClassNames[i] = Constants.IMAGENET_CLASSES[ix];
        mDiseaseName = Constants.IMAGENET_CLASSES[ix];
        topKScores[i] = scores[ix];
      }
      final long analysisDuration = SystemClock.elapsedRealtime() - startTime;
      return new AnalysisResult(topKClassNames, topKScores, moduleForwardDuration, analysisDuration);
    } catch (Exception e) {
      Log.e(Constants.TAG, "Error during image analysis", e);
      mAnalyzeImageErrorState = true;
      runOnUiThread(() -> {
        if (!isFinishing()) {
          showErrorDialog(v -> ImageClassificationActivity.this.finish());
        }
      });
      return null;
    }
  }

  @Override
  protected int getInfoViewCode() {
    return getIntent().getIntExtra(INTENT_INFO_VIEW_TYPE, -1);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mModule != null) {
      mModule.destroy();
    }
  }

}
