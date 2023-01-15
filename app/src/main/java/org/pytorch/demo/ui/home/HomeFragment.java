package org.pytorch.demo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.pytorch.demo.R;
import org.pytorch.demo.databinding.FragmentHomeBinding;
import org.pytorch.demo.models.Plant;
import org.pytorch.demo.models.Room;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    RecyclerView roomList,plantNeedCareList;
    MyRoomAdapter myRoomAdapter;
    PlantsAdapter plantsAdapter;
    ArrayList<Room> rooms;
    ArrayList<Plant> plants;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //
        roomList = binding.myRoom;
        rooms = new ArrayList<>();
        rooms.add(new Room("Living Room", 1, R.drawable.living_room, "3 cây trồng", "This is a living room"));
        rooms.add(new Room("Bedroom", 2, R.drawable.living_room, "2 cây trồng", "This is a bedroom"));
        rooms.add(new Room("Kitchen", 3, R.drawable.living_room, "1 cây trồng", "This is a kitchen"));
        rooms.add(new Room("Bathroom", 4, R.drawable.living_room, "1 cây trồng", "This is a bathroom"));
        myRoomAdapter = new MyRoomAdapter(rooms, this.getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        roomList.setLayoutManager(linearLayoutManager);
        roomList.setAdapter(myRoomAdapter);
        //
        plantNeedCareList = binding.needCare;
        plants = new ArrayList<>();
        plants.add(new Plant("Cây cảnh 1",1,R.drawable.living_room,"This is a plant",1,"Living Room","Tốt","1 giờ trước",3,"Không"));
        plants.add(new Plant("Cây cảnh 2",2,R.drawable.living_room,"This is a plant",1,"Living Room","Tốt","1 giờ trước",3,"Không"));
        plants.add(new Plant("Cây cảnh 3",3,R.drawable.living_room,"This is a plant",1,"Living Room","Tốt","1 giờ trước",3,"Không"));
        plants.add(new Plant("Cây cảnh 4",4,R.drawable.living_room,"This is a plant",1,"Living Room","Tốt","1 giờ trước",3,"Không"));
        plants.add(new Plant("Cây cảnh 5",5,R.drawable.living_room,"This is a plant",1,"Living Room","Tốt","1 giờ trước",3,"Không"));
        plantsAdapter = new PlantsAdapter(plants, this.getContext());
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        plantNeedCareList.setLayoutManager(linearLayoutManager1);
        plantNeedCareList.setAdapter(plantsAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}