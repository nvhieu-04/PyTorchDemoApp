package org.pytorch.demo.ui.room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import org.pytorch.demo.R;

public class SeeAllRoom extends AppCompatActivity {
    Button btnAddNewRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_room);
        //change color status bar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#DEEAD8"));
        btnAddNewRoom = findViewById(R.id.button_add_newRoom);
        btnAddNewRoom.setOnClickListener(v -> {
            startActivity(new Intent(SeeAllRoom.this, AddNewRoom.class));
        }
        );
    }
}