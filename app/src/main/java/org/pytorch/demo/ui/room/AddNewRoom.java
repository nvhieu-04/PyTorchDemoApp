package org.pytorch.demo.ui.room;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

import org.pytorch.demo.MainActivity;
import org.pytorch.demo.R;
import org.pytorch.demo.models.PlantRequest;
import org.pytorch.demo.models.PlantResponse;
import org.pytorch.demo.models.Room;
import org.pytorch.demo.models.RoomRequest;
import org.pytorch.demo.models.RoomResponse;
import org.pytorch.demo.ui.login.ApiClient;
import org.pytorch.demo.ui.plant.AddInformation;
import org.w3c.dom.Text;

import retrofit2.Call;

public class AddNewRoom extends AppCompatActivity {
    ImageView back, notification, imageRoom;
    String path;
    Uri uri;
    EditText nameRoom;
    Button addRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_room);
        imageRoom = findViewById(R.id.imageView_avatarRoom);
        imageRoom.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
        nameRoom = findViewById(R.id.editTextTextAddNameRoom);
        addRoom = findViewById(R.id.buttonAddNewRoom);
        imageRoom.setOnClickListener(v -> {
            //open gallery
            ImagePicker.with(this)
                    .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start(101);
        });
        //
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String token = sharedPreferences.getString("TOKEN", "");
        SharedPreferences sharedPreferences1 = getSharedPreferences("user", Context.MODE_PRIVATE);
        String id = sharedPreferences1.getString("id", null);
        //
        addRoom.setOnClickListener(v -> {
            String name = nameRoom.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter name room", Toast.LENGTH_SHORT).show();
            } else {
                //add new room
                Call<RoomResponse> call = ApiClient.getUserService().createRoom(token,new RoomRequest(name,id));
                call.enqueue(new retrofit2.Callback<RoomResponse>() {
                    @Override
                    public void onResponse(Call<RoomResponse> call, retrofit2.Response<RoomResponse> response) {
                        if (response.isSuccessful()) {
                            RoomResponse roomResponse = response.body();
                            if (roomResponse != null) {
                                if (roomResponse.getMsg().equals("Room created")) {
                                    Toast.makeText(AddNewRoom.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddNewRoom.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RoomResponse> call, Throwable t) {
                        Toast.makeText(AddNewRoom.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            uri = data.getData();
            imageRoom.setImageURI(uri);
            //You can get File object from intent
            //File file = ImagePicker.Companion.getFile(data);
            //You can also get File Path from intent
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}