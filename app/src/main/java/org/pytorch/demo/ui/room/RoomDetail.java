package org.pytorch.demo.ui.room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.pytorch.demo.R;
import org.pytorch.demo.models.Plant;
import org.pytorch.demo.ui.home.PlantsAdapter;
import org.pytorch.demo.ui.login.ApiClient;
import org.pytorch.demo.ui.plant.AddInformation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class RoomDetail extends AppCompatActivity {
    TextView nameRoom, countPlant;
    ImageView back, notification, imageRoom;
    RecyclerView recyclerView;
    PlantsAdapter plantsAdapter;
    Button addPlant;
    ArrayList<Plant> plants;
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
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("TOKEN", null);
        SharedPreferences sharedPreferences1 = getSharedPreferences("user", Context.MODE_PRIVATE);
        String id = sharedPreferences1.getString("id", null);
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        plants = new ArrayList<>();
        Call<List<Plant>> call1 = ApiClient.getUserService().getPlant(token);
        call1.enqueue(new retrofit2.Callback<List<Plant>>() {
            @Override
            public void onResponse(Call<List<Plant>> call, retrofit2.Response<List<Plant>> response) {
                if (response.isSuccessful()) {
                    List<Plant> plantList = response.body();
                    for (Plant plant : plantList) {
                        if(plant.getUserID().equals(id) && plant.getNameRoom().equals(name))
                        {
                            plants.add(plant);
                        }
                    }
                    plantsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Plant>> call, Throwable t) {

            }
        });
        plantsAdapter = new PlantsAdapter(plants, this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager1);
        recyclerView.setAdapter(plantsAdapter);

    }
}
