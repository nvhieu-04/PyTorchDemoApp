package org.pytorch.demo.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import org.pytorch.demo.R;
import org.pytorch.demo.models.Room;
import org.pytorch.demo.ui.room.RoomDetail;

import java.util.List;

public class MyRoomAdapter extends RecyclerView.Adapter<MyRoomAdapter.MyViewHolder> {
    List<Room> roomList;
    Context context;
    private static final String API_URL = "http://104.238.151.188:3000/";
    public MyRoomAdapter(List<Room> roomList, Context context) {
        this.roomList = roomList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyRoomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_myroom, parent, false);
        return new MyViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            Room room = roomList.get(position);
            holder.nameRoom.setText(room.getNameRoom());
            //holder.imageRoom.setImageResource(room.getImage());
            Glide
                .with(context)
                .load(API_URL+room.getImageRoom())
                .centerCrop()
                .transform((new RoundedCorners(20)))
                .placeholder(R.drawable.no_image_placeholder_svg)
                    .apply(new RequestOptions().override(100, 100))
                .into(holder.imageRoom);
            holder.countPlant.setText("Tầng: "+room.getFloor());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RoomDetail.class);
                    intent.putExtra("name",room.getNameRoom());
                    //intent.putExtra("count",room.getTotalPlant());
                    context.startActivity(intent);
                }}
            );
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView countPlant, nameRoom, description;
        public ImageView imageRoom;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            countPlant = itemView.findViewById(R.id.countPlant);
            nameRoom = itemView.findViewById(R.id.roomName);
            imageRoom = itemView.findViewById(R.id.roomImage);


        }
    }
}
