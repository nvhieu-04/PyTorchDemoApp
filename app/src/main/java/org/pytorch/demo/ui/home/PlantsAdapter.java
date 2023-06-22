package org.pytorch.demo.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import org.pytorch.demo.R;
import org.pytorch.demo.models.ImageDeleteRequest;
import org.pytorch.demo.models.Plant;
import org.pytorch.demo.models.PlantResponse;
import org.pytorch.demo.ui.login.ApiClient;
import org.pytorch.demo.ui.plant.PlantDetail;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.MyViewHolder> {
    List<Plant> plantList;
    Context context;
    private static final String API_URL = "http://104.238.151.188:3000/";

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
        holder.namePlant.setText(plant.getNamePlant());
        Glide
                .with(context)
                .load(API_URL+plant.getImagePlant())
                .centerCrop()
                .transform((new RoundedCorners(15)))
                .transition(com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions().override(100, 100))
                .placeholder(R.drawable.no_image_placeholder_svg)
                .into(holder.imagePlant);
        holder.nameRoom.setText(plant.getNameRoom());
        holder.healthyStatus.setText("Sức khỏe: "+plant.getHealthStatus());
        holder.age.setText("Ngày tạo: "+plant.getCreatedAt().substring(0,10)+" ngày");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlantDetail.class);
                intent.putExtra("name",plant.getNamePlant());
                intent.putExtra("room",plant.getNameRoom());
                intent.putExtra("age",plant.getCreatedAt());
                intent.putExtra("health",plant.getHealthStatus());
                intent.putExtra("id",plant.get_id());
                intent.putExtra("image",plant.getImagePlant());
                //intent.putExtra("image",plant.getImage());
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
        public ImageView imagePlant, btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            namePlant = itemView.findViewById(R.id.plantName);
            imagePlant = itemView.findViewById(R.id.imagePlant);
            btnDelete = itemView.findViewById(R.id.btn_DeletePlant);
            nameRoom = itemView.findViewById(R.id.plantRoomName);
            healthyStatus = itemView.findViewById(R.id.plantStatus);
            age = itemView.findViewById(R.id.plantAge);
            //
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("myKey", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("TOKEN", null);
            //
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Show pop up
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                    builder.setTitle("Xoá bỏ cây này");
                    builder.setMessage("Bạn muốn xoá chứ?");

                    builder.setPositiveButton("XOÁ", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
//                            Call<ResponseBody> deleteImage = ApiClient.getUserService().deleteImage(token, new ImageDeleteRequest(plantList.get(getAdapterPosition()).getImagePlant()));
//                            deleteImage.enqueue(new retrofit2.Callback<ResponseBody>() {
//                                @Override
//                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                                }
//                            });
                            Call<PlantResponse> call = ApiClient.getUserService().deletePlant(token,plantList.get(getAdapterPosition()).get_id());
                            call.enqueue(new retrofit2.Callback<PlantResponse>() {
                                @Override
                                public void onResponse(Call<PlantResponse> call, retrofit2.Response<PlantResponse> response) {
                                    if(response.isSuccessful()){
                                        Toast.makeText(view.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                        plantList.remove(getAdapterPosition());
                                        notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<PlantResponse> call, Throwable t) {
                                    Toast.makeText(view.getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });


                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("QUAY LẠI", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }

    }
}
