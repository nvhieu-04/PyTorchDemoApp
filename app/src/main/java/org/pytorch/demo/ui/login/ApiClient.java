package org.pytorch.demo.ui.login;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;
public class ApiClient {
    private static Retrofit getRetrofitInstance() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://172.30.176.222:3000/user/")
                .build();
        return retrofit;
    }

    public static UserService getUserService() {
        UserService userService = getRetrofitInstance().create(UserService.class);
        return userService;
    }
}
