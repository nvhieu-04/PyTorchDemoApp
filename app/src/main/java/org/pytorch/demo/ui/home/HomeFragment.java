package org.pytorch.demo.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.pytorch.demo.R;
import org.pytorch.demo.databinding.FragmentHomeBinding;
import org.pytorch.demo.models.Plant;
import org.pytorch.demo.models.Room;
import org.pytorch.demo.ui.login.ApiClient;
import org.pytorch.demo.ui.room.AddNewRoom;
import org.pytorch.demo.ui.room.SeeAllRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    RecyclerView roomList,plantNeedCareList;
    MyRoomAdapter myRoomAdapter;
    PlantsAdapter plantsAdapter;
    ArrayList<Room> rooms;
    ArrayList<Plant> plants;
    TextView seeAllRoom,seeAllPlantNeedCare;
    Button addRoom;
    ImageView search, scan;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        seeAllRoom = binding.seeAllPlantInRoom;
        seeAllPlantNeedCare = binding.seeAllPlantNeedCare;
        addRoom = binding.buttonaAddnewroomHome;
        //
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("TOKEN", null);
        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String id = sharedPreferences1.getString("id", null);
        seeAllRoom.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), SeeAllRoom.class));
                        //getActivity().overridePendingTransition(R.anim.anim_move_right, R.anim.anim_move_left);
                    }
                }
        );
        //
        roomList = binding.myRoom;
        rooms = new ArrayList<>();
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
                    if(rooms.size() > 0)
                    {
                        addRoom.setVisibility(View.GONE);
                    }
                    else
                    {
                        addRoom.setVisibility(View.VISIBLE);
                    }
                    myRoomAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {

            }
        });
        //
        myRoomAdapter = new MyRoomAdapter(rooms, this.getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        roomList.setLayoutManager(linearLayoutManager);
        roomList.setAdapter(myRoomAdapter);
        //
        plantNeedCareList = binding.needCare;
        plants = new ArrayList<>();
        //
        Call<List<Plant>> call1 = ApiClient.getUserService().getPlant(token);
        call1.enqueue(new retrofit2.Callback<List<Plant>>() {
            @Override
            public void onResponse(Call<List<Plant>> call, retrofit2.Response<List<Plant>> response) {
                if (response.isSuccessful()) {
                    List<Plant> plantList = response.body();
                    for (Plant plant : plantList) {
                        if(plant.getUserID().equals(id))
                        {
                            if (!plant.getHealthStatus().equals("Healthy"))
                            {
                                plants.add(plant);
                            }
                        }
                    }
                    plantsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Plant>> call, Throwable t) {

            }
        });
        //
        plantsAdapter = new PlantsAdapter(plants, this.getContext());
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        plantNeedCareList.setLayoutManager(linearLayoutManager1);
        plantNeedCareList.setAdapter(plantsAdapter);
        addRoom.setOnClickListener(
                v -> startActivity(new Intent(getContext(), AddNewRoom.class))
        );
        // Wait for the view to be created
        return root;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        binding.searchIcon.setOnClickListener(
                v -> navController.navigate(R.id.action_navigation_home_to_navigation_search)
        );
        binding.addPlant.setOnClickListener(
                v -> navController.navigate(R.id.action_navigation_home_to_navigation_camera)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}