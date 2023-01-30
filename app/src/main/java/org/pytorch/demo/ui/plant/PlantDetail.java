package org.pytorch.demo.ui.plant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.pytorch.demo.R;

public class PlantDetail extends AppCompatActivity {
    ImageView imagePlant, back;
    TextView namePlant, nameRoom, age, healthyStatus;
    Button btnEdit, btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_plant);
        //change color status bar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#DEEAD8"));
        //
        imagePlant = findViewById(R.id.imagePlantDetail);
        namePlant = findViewById(R.id.namePlant_Detail);
        nameRoom = findViewById(R.id.roomName_Detail);
        age = findViewById(R.id.agePlant_Detail);
        healthyStatus = findViewById(R.id.statusPlant_Detail);
        btnEdit = findViewById(R.id.updateStatus_Detail);
        btnDelete = findViewById(R.id.deletePlant_Detail);
        back = findViewById(R.id.backbtnPlant_Detail);
        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String room = i.getStringExtra("room");
        String agePlant = i.getStringExtra("age");
        String status = i.getStringExtra("health");
        int image = i.getIntExtra("image", 0);
        namePlant.setText(name);
        nameRoom.setText(room);
        age.setText(agePlant);
        healthyStatus.setText(status);
        imagePlant.setImageResource(image);

        back.setOnClickListener(
                v -> {
                    onBackPressed();
                }
        );



    }

}
