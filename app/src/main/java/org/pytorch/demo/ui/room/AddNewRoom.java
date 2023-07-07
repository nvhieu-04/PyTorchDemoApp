package org.pytorch.demo.ui.room;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.pytorch.demo.MainActivity;
import org.pytorch.demo.R;
import org.pytorch.demo.models.ImageResultResponse;
import org.pytorch.demo.models.PlantRequest;
import org.pytorch.demo.models.PlantResponse;
import org.pytorch.demo.models.Room;
import org.pytorch.demo.models.RoomRequest;
import org.pytorch.demo.models.RoomResponse;
import org.pytorch.demo.ui.login.ApiClient;
import org.pytorch.demo.ui.plant.AddInformation;
import org.w3c.dom.Text;

import java.io.File;
import java.sql.Time;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class AddNewRoom extends AppCompatActivity {
    ImageView back, notification, imageRoom;
    Uri uri;
    EditText nameRoom, floorRoom;
    Button addRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_room);
        imageRoom = findViewById(R.id.imageView_avatarRoom);
        imageRoom.setImageResource(R.drawable.icon_garden);
        nameRoom = findViewById(R.id.editTextTextAddNameRoom);
        addRoom = findViewById(R.id.buttonAddNewRoom);
        floorRoom = findViewById(R.id.editTextTextAddNameRoomFloor);
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
            String floor = "update later";
            if (name.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền tên vào ô trống", Toast.LENGTH_SHORT).show();
            } else {
                if(uri == null){
                    Toast.makeText(this, "Vui lòng thêm hình vào", Toast.LENGTH_SHORT).show();
                    return;
                }
                File file = new File(uri.getPath());
                String nameFile = UUID.randomUUID().toString() + id + ".jpg";
                // create RequestBody instance from file
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);
                // MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("image", nameFile , requestFile);
                Call<ImageResultResponse> call1 = ApiClient.getUserService().uploadImage(body);

                call1.enqueue(new retrofit2.Callback<ImageResultResponse>() {
                    @Override
                    public void onResponse(Call<ImageResultResponse> call, retrofit2.Response<ImageResultResponse> responseq) {
                        if (responseq.isSuccessful()) {
                            ImageResultResponse imageResultResponse = responseq.body();

                        }
                    }
                    @Override
                    public void onFailure(Call<ImageResultResponse> call, Throwable t) {
                        Toast.makeText(AddNewRoom.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
                //add new room

                Call<RoomResponse> call = ApiClient.getUserService().createRoom(token, new RoomRequest(name, id, nameFile,floor));
                call.enqueue(new retrofit2.Callback<RoomResponse>() {
                    @Override
                    public void onResponse(Call<RoomResponse> call, retrofit2.Response<RoomResponse> response) {
                        if (response.isSuccessful()) {
                            RoomResponse roomResponse = response.body();
                            if (roomResponse != null) {
                                if (roomResponse.getMsg().equals("Room created")) {
                                    Toast.makeText(AddNewRoom.this, roomResponse.getMsg(), Toast.LENGTH_SHORT).show();
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