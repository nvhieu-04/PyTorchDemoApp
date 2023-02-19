package org.pytorch.demo.ui.login;

import org.pytorch.demo.models.ImageResultResponse;
import org.pytorch.demo.models.Plant;
import org.pytorch.demo.models.PlantRequest;
import org.pytorch.demo.models.PlantResponse;
import org.pytorch.demo.models.Room;
import org.pytorch.demo.models.RoomResponse;
import org.pytorch.demo.models.UserInfo;
import org.pytorch.demo.models.RoomRequest;
import org.pytorch.demo.ui.register.RegisterRequest;
import org.pytorch.demo.ui.register.RegisterResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import  retrofit2.Call;
import  retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import  retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    @POST("signup")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);
    @POST("createRoom")
    Call<RoomResponse> createRoom(@Header("token") String token, @Body RoomRequest roomRequest);
    @POST("createPlant")
    Call<PlantResponse> createPlant(@Header("token") String token, @Body PlantRequest plantRequest);

    @GET("me")
    Call<UserInfo> me(@Header("token") String token);
    @GET("getPlant")
    Call<List<Plant>> getPlant(@Header("token") String token);
    @GET("getRoom")
    Call<List<Room>> getRoom(@Header("token") String token);

    @DELETE("deletePlant/{id}")
    Call<PlantResponse> deletePlant(@Header("token") String token, @Path("id") String id);
    @DELETE("deleteRoom/{id}")
    Call<RoomResponse> deleteRoom(@Header("token") String token, @Path("id") String id);

    @PUT("updatePlant/{id}")
    Call<PlantResponse> updatePlant(@Header("token") String token, @Path("id") String id, @Body PlantRequest plantRequest);
    @PUT("updateRoom/{id}")
    Call<RoomResponse> updateRoom(@Header("token") String token, @Path("id") String id, @Body RoomRequest roomRequest);

    @Multipart
    @POST("uploadImage")
    Call<ImageResultResponse> uploadImage(@Part MultipartBody.Part image);

}
