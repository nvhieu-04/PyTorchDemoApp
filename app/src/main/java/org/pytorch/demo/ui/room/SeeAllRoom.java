package org.pytorch.demo.ui.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import org.pytorch.demo.R;
import org.pytorch.demo.models.Room;
import org.pytorch.demo.ui.home.ListMyRoomAdapter;
import org.pytorch.demo.ui.home.MyRoomAdapter;
import org.pytorch.demo.ui.login.ApiClient;
import org.pytorch.demo.ui.plant.AddInformation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class SeeAllRoom extends AppCompatActivity {
    Button btnAddNewRoom;
    RecyclerView roomList;
    ListMyRoomAdapter myRoomAdapter;
    ArrayList<Room> rooms;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_room);
        roomList = findViewById(R.id.recyclerViewSeeAllroom);
        back = findViewById(R.id.imageViewBackInAllRoom);
        rooms = new ArrayList<>();
        //
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String token = sharedPreferences.getString("TOKEN", "");
        SharedPreferences sharedPreferences1 = getSharedPreferences("user", Context.MODE_PRIVATE);
        String id = sharedPreferences1.getString("id", null);
        //
        //change color status bar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#DDFFBB"));
        btnAddNewRoom = findViewById(R.id.button_add_newRoom);
        btnAddNewRoom.setOnClickListener(v -> {
            startActivity(new Intent(SeeAllRoom.this, AddNewRoom.class));
        }
        );
        //
        Call<List<Room>> call = ApiClient.getUserService().getRoom(token);
        call.enqueue(new retrofit2.Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, retrofit2.Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    List<Room> roomList = response.body();
                    for (Room room : roomList) {
                        if(room.getIdUser().equals(id))
                        {
                            rooms.add(room);
                        }
                    }
                    myRoomAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {

            }
        });
        //
        myRoomAdapter = new ListMyRoomAdapter(rooms, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        roomList.setLayoutManager(linearLayoutManager);
        roomList.setAdapter(myRoomAdapter);
        //
        back.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        onBackPressed();
                    }
                }
        );
    }
}