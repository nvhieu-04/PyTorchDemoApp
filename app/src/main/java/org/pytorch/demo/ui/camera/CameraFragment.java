package org.pytorch.demo.ui.camera;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.pytorch.demo.InfoViewFactory;
import org.pytorch.demo.R;
import org.pytorch.demo.vision.ImageClassificationActivity;
import org.pytorch.demo.vision.VisionListActivity;

public class CameraFragment extends Fragment {


    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle  savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

            final Intent intent = new Intent(getActivity(), ImageClassificationActivity.class);
            intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME,
                    "levit_128.ptl");
            intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                    InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
            startActivity(intent);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}