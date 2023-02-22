package org.pytorch.demo.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import org.pytorch.demo.R;
import org.pytorch.demo.models.ImageDeleteRequest;
import org.pytorch.demo.models.PlantResponse;
import org.pytorch.demo.models.Room;
import org.pytorch.demo.models.RoomRequest;
import org.pytorch.demo.models.RoomResponse;
import org.pytorch.demo.ui.login.ApiClient;
import org.pytorch.demo.ui.room.RoomDetail;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ListMyRoomAdapter extends RecyclerView.Adapter<ListMyRoomAdapter.MyViewHolder>{
    List<Room> roomList;
    Context context;
    private static final String API_URL = "http://104.238.151.188:3000/";
    public ListMyRoomAdapter(List<Room> roomList, Context context) {
        this.roomList = roomList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListMyRoomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_allmyroom, parent, false);
        return new MyViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMyRoomAdapter.MyViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.nameRoom.setText(room.getNameRoom());
        Glide
                .with(context)
                .load(API_URL+room.getImageRoom())
                .centerCrop()
                .transform(new RoundedCorners(20))
                .transition(com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade(500))
                .placeholder(R.drawable.no_image_placeholder_svg)
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
        public ImageView imageRoom, btnDetete, btnEdit;
        public String m_Text = "";
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            countPlant = itemView.findViewById(R.id.countPlantInRoom);
            nameRoom = itemView.findViewById(R.id.roomNameInRoom);
            imageRoom = itemView.findViewById(R.id.imageViewInRoom);
            btnDetete = itemView.findViewById(R.id.buttonDeleteRoom);
            btnEdit = itemView.findViewById(R.id.buttonEditNameRoom);

            //
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("myKey", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("TOKEN", null);
            //
            btnDetete.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //deleteRoom(token, room.getId());
                            //Show pop up
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                            builder.setTitle("Xoá bỏ phòng này");
                            builder.setMessage("Bạn muốn xoá chứ?");

                            builder.setPositiveButton("XOÁ", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing but close the dialog
//                                    Call<ResponseBody> deleteImage = ApiClient.getUserService().deleteImage(token, new ImageDeleteRequest(roomList.get(getAdapterPosition()).getImageRoom()));
//                                    deleteImage.enqueue(new retrofit2.Callback<ResponseBody>() {
//                                        @Override
//                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                                        }
//                                    });
                                    Call<RoomResponse> call = ApiClient.getUserService().deleteRoom(token,roomList.get(getAdapterPosition()).get_id());
                                    call.enqueue(new retrofit2.Callback<RoomResponse>() {
                                        @Override
                                        public void onResponse(Call<RoomResponse> call, retrofit2.Response<RoomResponse> response) {
                                            if(response.isSuccessful()){
                                                Toast.makeText(view.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                                roomList.remove(getAdapterPosition());
                                                notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<RoomResponse> call, Throwable t) {
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
                    }
            );
            btnEdit.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setTitle("Nhập tên phòng bạn muốn đổi");

                            final EditText input = new EditText(view.getContext());
                            input.setInputType(InputType.TYPE_CLASS_TEXT);
                            builder.setView(input);

                            builder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    m_Text = input.getText().toString();
                                    Call<RoomResponse> call = ApiClient.getUserService().updateRoom(token,roomList.get(getAdapterPosition()).get_id(),new RoomRequest(m_Text,roomList.get(getAdapterPosition()).getIdUser(),roomList.get(getAdapterPosition()).getImageRoom(),roomList.get(getAdapterPosition()).getFloor()));
                                    call.enqueue(new retrofit2.Callback<RoomResponse>() {
                                        @Override
                                        public void onResponse(Call<RoomResponse> call, retrofit2.Response<RoomResponse> response) {
                                            if(response.isSuccessful()){
                                                Toast.makeText(view.getContext(), "Đổi tên thành công", Toast.LENGTH_SHORT).show();
                                                roomList.get(getAdapterPosition()).setNameRoom(m_Text);
                                                notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<RoomResponse> call, Throwable t) {
                                            Toast.makeText(view.getContext(), "Đổi tên thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                            builder.setNegativeButton("Quay Lại", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.show();
                        }
                    }
            );
        }
    }
}
