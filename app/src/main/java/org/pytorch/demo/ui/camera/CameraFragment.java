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
import java.util.Objects;

public class CameraFragment extends Fragment {
    private static final String API_URL = "http://104.238.151.188:3000/";
    ListCardView levit, deepvit, simplevit, mobilevit, crossvit ,test;

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
        levit = view.findViewById(R.id.vision_card_DeiT_click_area);
        deepvit = view.findViewById(R.id.vision_card_DeepViT_click_area);
        simplevit = view.findViewById(R.id.vision_card_SimpleViT_click_area);
        mobilevit = view.findViewById(R.id.vision_card_MobileViT_click_area);
        crossvit = view.findViewById(R.id.vision_card_CrossViT_click_area);
        test = view.findViewById(R.id.vision_card_Test_click_area);
        levit.setOnClickListener(
                v -> {
                    if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        ProgressDialog pd = new ProgressDialog(getActivity());
                        pd.setTitle("Đang tải model");
                        pd.setMessage("Vui lòng chờ....");
                        pd.setCancelable(false);
                        pd.setIndeterminate(true);
                        pd.show();
                        String fileName = "levit_256.pt";
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                        Toast.makeText(getActivity(),"Name file: " + file, Toast.LENGTH_SHORT).show();
                        Constants.IMAGENET_CLASSES = new String[]{
                                "Bệnh đốm lá xám",
                                "Bệnh gỉ sắt",
                                "Bệnh cháy lá",
                                "Cây khỏe mạnh",
                        };
                        if (file.exists()) {
                            final Intent intent = new Intent(getActivity(), ImageClassificationActivity.class);
                            intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, fileName);
                            intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                                    InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
                            pd.dismiss();
                            startActivity(intent);
                        } else {
                            DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(API_URL + "levit_256_1.pt"));
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setTitle(fileName);
                            request.setDescription("Downloading File");
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, "levit_256.pt");
                            downloadManager.enqueue(request);
                            BroadcastReceiver onComplete = new BroadcastReceiver() {
                                public void onReceive(Context ctxt, Intent intent) {
                                    final Intent intent1 = new Intent(getActivity(), ImageClassificationActivity.class);
                                    intent1.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, fileName);
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
        );
        simplevit.setOnClickListener(
                view1 -> {
                    if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        ProgressDialog pd = new ProgressDialog(getActivity());
                        pd.setTitle("Đang tải model");
                        pd.setMessage("Vui lòng chờ....");
                        pd.setCancelable(false);
                        pd.setIndeterminate(true);
                        pd.show();
                        String fileName = "mobile_simple.pt";
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                        Toast.makeText(getActivity(),"Name file: " + file, Toast.LENGTH_SHORT).show();
                        Constants.IMAGENET_CLASSES = new String[]{
                                "Bệnh đốm lá xám",
                                "Bệnh gỉ sắt",
                                "Bệnh cháy lá",
                                "Cây khỏe mạnh",
                        };
                        if (file.exists()) {
                            final Intent intent = new Intent(getActivity(), ImageClassificationActivity.class);
                            intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, fileName);
                            intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                                    InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
                            pd.dismiss();
                            startActivity(intent);
                        } else {
                            DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(API_URL + "mobile_simple.pt"));
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setTitle(fileName);
                            request.setDescription("Downloading File");
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, "mobile_simple.pt");
                            downloadManager.enqueue(request);
                            BroadcastReceiver onComplete = new BroadcastReceiver() {
                                public void onReceive(Context ctxt, Intent intent) {
                                    final Intent intent1 = new Intent(getActivity(), ImageClassificationActivity.class);
                                    intent1.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, fileName);
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
        );
        deepvit.setOnClickListener(
                view12 -> {
                    if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        ProgressDialog pd = new ProgressDialog(getActivity());
                        pd.setTitle("Đang tải model");
                        pd.setMessage("Vui lòng chờ....");
                        pd.setCancelable(false);
                        pd.setIndeterminate(true);
                        pd.show();
                        String fileName = "mobile_deep.pt";
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                        Toast.makeText(getActivity(),"Name file: " + file, Toast.LENGTH_SHORT).show();
                        Constants.IMAGENET_CLASSES = new String[]{
                                "Bệnh đốm lá xám",
                                "Bệnh gỉ sắt",
                                "Bệnh cháy lá",
                                "Cây khỏe mạnh",
                        };
                        if (file.exists()) {
                            final Intent intent = new Intent(getActivity(), ImageClassificationActivity.class);
                            intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, fileName);
                            intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                                    InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
                            pd.dismiss();
                            startActivity(intent);
                        } else {
                            DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(API_URL + "mobile_deep.pt"));
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setTitle(fileName);
                            request.setDescription("Downloading File");
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, "mobile_deep.pt");
                            downloadManager.enqueue(request);
                            BroadcastReceiver onComplete = new BroadcastReceiver() {
                                public void onReceive(Context ctxt, Intent intent) {
                                    final Intent intent1 = new Intent(getActivity(), ImageClassificationActivity.class);
                                    intent1.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, fileName);
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
        );
        mobilevit.setOnClickListener(
                view13 -> {
                    if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        ProgressDialog pd = new ProgressDialog(getActivity());
                        pd.setTitle("Đang tải model");
                        pd.setMessage("Vui lòng chờ....");
                        pd.setCancelable(false);
                        pd.setIndeterminate(true);
                        pd.show();
                        String fileName = "mobilevit_xs.pt";
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                        Toast.makeText(getActivity(),"Name file: " + file, Toast.LENGTH_SHORT).show();
                        Constants.IMAGENET_CLASSES = new String[]{
                                "Bệnh đốm lá xám",
                                "Bệnh gỉ sắt",
                                "Bệnh cháy lá",
                                "Cây khỏe mạnh",
                        };
                        if (file.exists()) {
                            final Intent intent = new Intent(getActivity(), ImageClassificationActivity.class);
                            intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, fileName);
                            intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                                    InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
                            pd.dismiss();
                            startActivity(intent);
                        } else {
                            DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(API_URL + "mobilevit_xs.pt"));
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setTitle(fileName);
                            request.setDescription("Downloading File");
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, "mobilevit_xs.pt");
                            downloadManager.enqueue(request);
                            BroadcastReceiver onComplete = new BroadcastReceiver() {
                                public void onReceive(Context ctxt, Intent intent) {
                                    final Intent intent1 = new Intent(getActivity(), ImageClassificationActivity.class);
                                    intent1.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, fileName);
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
        );
        crossvit.setOnClickListener(
                view14 -> {
                    if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        ProgressDialog pd = new ProgressDialog(getActivity());
                        pd.setTitle("Đang tải model");
                        pd.setMessage("Vui lòng chờ....");
                        pd.setCancelable(false);
                        pd.setIndeterminate(true);
                        pd.show();
                        String fileName = "crossvit.pt";
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                        Toast.makeText(getActivity(),"Name file: " + file, Toast.LENGTH_SHORT).show();
                        Constants.IMAGENET_CLASSES = new String[]{
                                "Bệnh đốm lá xám",
                                "Bệnh gỉ sắt",
                                "Bệnh cháy lá",
                                "Cây khỏe mạnh",
                        };
                        if (file.exists()) {
                            final Intent intent = new Intent(getActivity(), ImageClassificationActivity.class);
                            intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, fileName);
                            intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                                    InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
                            pd.dismiss();
                            startActivity(intent);
                        } else {
                            DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(API_URL + "crossvit.pt"));
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setTitle(fileName);
                            request.setDescription("Downloading File");
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, "crossvit.pt");
                            downloadManager.enqueue(request);
                            BroadcastReceiver onComplete = new BroadcastReceiver() {
                                public void onReceive(Context ctxt, Intent intent) {
                                    final Intent intent1 = new Intent(getActivity(), ImageClassificationActivity.class);
                                    intent1.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, fileName);
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
        );

        test.setOnClickListener(
                view15 -> {
                    if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        ProgressDialog pd = new ProgressDialog(getActivity());
                        pd.setTitle("Đang tải model");
                        pd.setMessage("Vui lòng chờ....");
                        pd.setCancelable(false);
                        pd.setIndeterminate(true);
                        pd.show();
                        String fileName = "sample.ptl";
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                        Toast.makeText(getActivity(),"Name file: " + file, Toast.LENGTH_SHORT).show();
                        Constants.IMAGENET_CLASSES = new String[]{
                                "Bệnh đốm lá xám",
                                "Bệnh gỉ sắt",
                                "Bệnh cháy lá",
                                "Cây khỏe mạnh",
                        };
                        if (file.exists()) {
                            final Intent intent = new Intent(getActivity(), ImageClassificationActivity.class);
                            intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, fileName);
                            intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                                    InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
                            pd.dismiss();
                            startActivity(intent);
                        } else {
                            DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(API_URL + "sample.ptl"));
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setTitle(fileName);
                            request.setDescription("Downloading File");
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, "sample.ptl");
                            downloadManager.enqueue(request);
                            BroadcastReceiver onComplete = new BroadcastReceiver() {
                                public void onReceive(Context ctxt, Intent intent) {
                                    final Intent intent1 = new Intent(getActivity(), ImageClassificationActivity.class);
                                    intent1.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, fileName);
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
        );
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}