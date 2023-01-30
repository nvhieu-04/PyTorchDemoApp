package org.pytorch.demo.ui.plant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.pytorch.demo.R;

public class AddInformation extends AppCompatActivity {
    ImageView back,notification, imagePlant;
    Button addPlant, updatePlant;
    EditText namePlant, nameRoomPlant, statusPlant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);
        //change color status bar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#DEEAD8"));
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
        }
        else {
            statusPlant.setText(nameDisease);
        }
        addPlant.setOnClickListener(v -> {
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            onBackPressed();
        });
        updatePlant.setOnClickListener(v -> {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            onBackPressed();
        });
    }
}