package org.pytorch.demo.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.pytorch.demo.R;
import org.pytorch.demo.models.Plant;
import org.pytorch.demo.ui.plant.PlantDetail;

import java.util.List;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.MyViewHolder> {
    List<Plant> plantList;
    Context context;
    public PlantsAdapter(List<Plant> plantList, Context context) {
        this.plantList = plantList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlantsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_plant, parent, false);
        return new PlantsAdapter.MyViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsAdapter.MyViewHolder holder, int position) {
        Plant plant = plantList.get(position);
        holder.namePlant.setText(plant.getName());
        holder.imagePlant.setImageResource(plant.getImage());
        holder.nameRoom.setText(plant.getRoomName());
        holder.healthyStatus.setText("Sức khỏe: "+plant.getHealthStatus());
        holder.age.setText("Tuổi: "+plant.getAge()+" ngày");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlantDetail.class);
                intent.putExtra("name",plant.getName());
                intent.putExtra("room",plant.getRoomName());
                intent.putExtra("age","Tuổi: "+plant.getAge()+" ngày");
                intent.putExtra("health","Sức khỏe: "+plant.getHealthStatus());
                intent.putExtra("image",plant.getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView namePlant,nameRoom,healthyStatus,age;
        public ImageView imagePlant;
        public Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            namePlant = itemView.findViewById(R.id.plantName);
            imagePlant = itemView.findViewById(R.id.imagePlant);
            btnDelete = itemView.findViewById(R.id.btn_DeletePlant);
            nameRoom = itemView.findViewById(R.id.plantRoomName);
            healthyStatus = itemView.findViewById(R.id.plantStatus);
            age = itemView.findViewById(R.id.plantAge);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(),
                                    namePlant.getText() +" | "
                                            + " Demo function", Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }

    }
}
