package org.pytorch.demo.ui.plant;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

import org.pytorch.demo.InfoViewFactory;
import org.pytorch.demo.R;
import org.pytorch.demo.vision.ImageClassificationActivity;

public class AddInformation extends AppCompatActivity {
    ImageView back,notification, imagePlant;
    Button addPlant, updatePlant;
    EditText namePlant, nameRoomPlant, statusPlant;
    String path;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);
        //change color status bar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#DEEAD8"));
        //
        back = findViewById(R.id.back_title_information);
        addPlant = findViewById(R.id.button_add_plant_information);
        updatePlant = findViewById(R.id.button_update_plant_information);
        namePlant = findViewById(R.id.editText_NamePlant);
        nameRoomPlant = findViewById(R.id.editText_NameRoom);
        statusPlant = findViewById(R.id.editText_StatusPlant);
        imagePlant = findViewById(R.id.imageView_avatarPlant);
        imagePlant.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
        back.setOnClickListener(v -> {
            onBackPressed();
        });
        //
        Intent intent = getIntent();
        String nameDisease = intent.getStringExtra("diseaseName");
        String nameRoom = intent.getStringExtra("nameRoom");

        if (nameRoom != null)
        {
            nameRoomPlant.setText(nameRoom);
        }
        if (nameDisease == null)
        {
            updatePlant.setVisibility(Button.INVISIBLE);
            //statusPlant.setEnabled(false);
        }
        else {
            statusPlant.setText(nameDisease);
        }
        statusPlant.setOnClickListener(v -> {
            Intent intent1 = new Intent(AddInformation.this, ImageClassificationActivity.class);
            intent1.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME,
                    "levit_128.ptl");
            intent1.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                    InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
            intent1.putExtra("nameRoom", nameRoomPlant.getText().toString());
            startActivity(intent1);
        });
        addPlant.setOnClickListener(v -> {
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            onBackPressed();
        });
        updatePlant.setOnClickListener(v -> {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            onBackPressed();
        });
        imagePlant.setOnClickListener(v -> {
            ImagePicker.with(this)
                    .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start(101);
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            uri = data.getData();
            imagePlant.setImageURI(uri);
            //You can get File object from intent
            //File file = ImagePicker.Companion.getFile(data);
            //You can also get File Path from intent
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }


}