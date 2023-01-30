package org.pytorch.demo.ui.room;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.pytorch.demo.R;
import org.pytorch.demo.ui.plant.AddInformation;

public class RoomDetail extends AppCompatActivity {
    TextView nameRoom, countPlant;
    ImageView back, notification, imageRoom;
    RecyclerView recyclerView;
    Button addPlant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        //change color status bar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#DEEAD8"));
        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String count = i.getStringExtra("count");
        nameRoom = findViewById(R.id.roomNameDetail);
        back = findViewById(R.id.back_RoomDetail);
        notification = findViewById(R.id.imageView16);
        recyclerView = findViewById(R.id.detailPlant_Room);
        addPlant = findViewById(R.id.button_AddPlant);
        nameRoom.setText(name);
        back.setOnClickListener(v -> {
            onBackPressed();
        });
        addPlant.setOnClickListener(v -> {
            Intent intent = new Intent(RoomDetail.this, AddInformation.class);
            intent.putExtra("nameRoom", name);
            startActivity(intent);
        });

    }
}
