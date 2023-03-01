package org.pytorch.demo.ui.plant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.bumptech.glide.Glide;

import org.pytorch.demo.MainActivity;
import org.pytorch.demo.R;
import org.pytorch.demo.models.PlantResponse;
import org.pytorch.demo.ui.login.ApiClient;

import retrofit2.Call;

public class PlantDetail extends AppCompatActivity {
    public static final String API_URL = "http://104.238.151.188:3000/";
    ImageView imagePlant, back;
    TextView namePlant, nameRoom, age, healthyStatus;
    Button btnEdit, btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_plant);
        //change color status bar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#DEEAD8"));
        //
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("TOKEN", null);
        //
        imagePlant = findViewById(R.id.imagePlantDetail);
        namePlant = findViewById(R.id.namePlant_Detail);
        nameRoom = findViewById(R.id.roomName_Detail);
        age = findViewById(R.id.agePlant_Detail);
        healthyStatus = findViewById(R.id.statusPlant_Detail);
        btnEdit = findViewById(R.id.updateStatus_Detail);
        btnDelete = findViewById(R.id.deletePlant_Detail);
        back = findViewById(R.id.backbtnPlant_Detail);
        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String room = i.getStringExtra("room");
        String agePlant = i.getStringExtra("age");
        String status = i.getStringExtra("health");
        String idPlant = i.getStringExtra("id");
        String image = i.getStringExtra("image");
        namePlant.setText(name);
        nameRoom.setText(room);
        age.setText(agePlant);
        healthyStatus.setText(status);
        Glide
                .with(this)
                .load(API_URL+image)
                .centerCrop()
                .placeholder(R.drawable.bg_error_dialog)
                .into(imagePlant);

        back.setOnClickListener(
                v -> {
                    onBackPressed();
                }
        );
        btnDelete.setOnClickListener(
                v -> {
                    //Show pop up
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setTitle("Xoá bỏ cây này");
                    builder.setMessage("Bạn muốn xoá chứ?");

                    builder.setPositiveButton("XOÁ", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            Call<PlantResponse> call = ApiClient.getUserService().deletePlant(token,idPlant);
                            call.enqueue(new retrofit2.Callback<PlantResponse>() {
                                @Override
                                public void onResponse(Call<PlantResponse> call, retrofit2.Response<PlantResponse> response) {
                                    if(response.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(PlantDetail.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                                @Override
                                public void onFailure(Call<PlantResponse> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
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
                });
        btnEdit.setOnClickListener(
                v -> {
                    Intent intent = new Intent(PlantDetail.this, AddInformation.class);
                    intent.putExtra("namePlant", name);
                    intent.putExtra("nameRoom", room);
                    intent.putExtra("idPlant", idPlant);
                    intent.putExtra("diseaseName", status);
                    intent.putExtra("imageDetail", image);
                    intent.putExtra("edit", true);
                    startActivity(intent);
                }
        );
    }

}
