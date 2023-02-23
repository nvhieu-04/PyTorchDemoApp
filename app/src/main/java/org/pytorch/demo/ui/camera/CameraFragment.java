package org.pytorch.demo.ui.camera;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.pytorch.demo.Constants;
import org.pytorch.demo.InfoViewFactory;
import org.pytorch.demo.ListCardView;
import org.pytorch.demo.R;
import org.pytorch.demo.vision.ImageClassificationActivity;
import org.pytorch.demo.vision.VisionListActivity;

import java.io.File;

public class CameraFragment extends Fragment {
    private static final String API_URL = "http://104.238.151.188:3000/";
    ListCardView levit128;

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        levit128 = view.findViewById(R.id.vision_card_DeiT_click_area);

        levit128.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            // this will request for permission when permission is not true
                        } else {
                            ProgressDialog pd = new ProgressDialog(getActivity());
                            pd.setTitle("Đang đang tải model");
                            pd.setMessage("Vui lòng chờ....");
                            pd.setCancelable(false);
                            pd.setIndeterminate(true);
                            pd.show();
                            String fileName = "levit_128.pt";
                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                            Toast.makeText(getActivity(),"Name file: " + file, Toast.LENGTH_SHORT).show();
                            Constants.IMAGENET_CLASSES = new String[]{
                                    "0:  Gray_leaf_spot",
                                    "1: Common_rust",
                                    "2: Northern_Leaf_Blight",
                                    "3: Healthy",
                            };
                            if (file.exists()) {
                                final Intent intent = new Intent(getActivity(), ImageClassificationActivity.class);
                                intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, file);
                                intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                                        InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
                                pd.dismiss();
                                startActivity(intent);
                            } else {
                                DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(API_URL + "levit_128.pt"));
                                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                                request.setTitle("Download " + fileName);
                                request.setDescription("Downloading File");
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, "levit_128.pt");
                                downloadManager.enqueue(request);
                                BroadcastReceiver onComplete = new BroadcastReceiver() {
                                    public void onReceive(Context ctxt, Intent intent) {
                                        final Intent intent1 = new Intent(getActivity(), ImageClassificationActivity.class);
                                        intent1.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, file);
                                        intent1.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                                                InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
                                        pd.dismiss();
                                        startActivity(intent1);
                                    }
                                };
                                getActivity().registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                            }
                        }

                    }
                }
        );
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}