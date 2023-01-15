package org.pytorch.demo.ui.room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.pytorch.demo.R;

public class RoomDetail extends AppCompatActivity {
    TextView nameRoom, countPlant;
    ImageView back, notification, imageRoom;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String count = i.getStringExtra("count");
        nameRoom = findViewById(R.id.roomNameDetail);
        back = findViewById(R.id.back_RoomDetail);
        notification = findViewById(R.id.imageView16);
        recyclerView = findViewById(R.id.detailPlant_Room);
        nameRoom.setText(name);
        back.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}