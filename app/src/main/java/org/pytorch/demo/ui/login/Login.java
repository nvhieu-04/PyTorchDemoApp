package org.pytorch.demo.ui.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.pytorch.demo.MainActivity;
import org.pytorch.demo.R;
import org.pytorch.demo.ui.register.Register;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText email, password;
    Button login;
    TextView register, forgotPassword;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.buttonLogin);
        register = findViewById(R.id.textViewRegister);
        forgotPassword = findViewById(R.id.textViewForgetPassword);
        progressBar = findViewById(R.id.progressBarLogin);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#DEEAD8"));
        ImageView backLogin = findViewById(R.id.back_Login);
        backLogin.setOnClickListener(v -> {
            onBackPressed();
        });
        register.setOnClickListener(
                v -> {
                    Intent intent = new Intent(Login.this, Register.class);
                    startActivity(intent);
                }
        );
        login.setOnClickListener(v -> {
                    String email = this.email.getText().toString();
                    String password = this.password.getText().toString();
                    progressBar.setVisibility(android.view.View.VISIBLE);
                    login.setVisibility(android.view.View.INVISIBLE);
                    if (email.isEmpty() || password.isEmpty()) {
                        login.setVisibility(android.view.View.VISIBLE);
                        return;
                    }
                    ProgressDialog pd = new ProgressDialog(this);
                    pd.setTitle("Đang đăng nhập...");
                    pd.setMessage("Vui lòng chờ.");
                    pd.setCancelable(true);
                    pd.setIndeterminate(true);
                    pd.show();
                    LoginRequest loginRequest = new LoginRequest(email, password);
                    Call<LoginResponse> call = ApiClient.getUserService().login(loginRequest);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                LoginResponse loginResponse = response.body();
                                if (loginResponse != null) {
                                    String token = loginResponse.getToken();
                                    String message = loginResponse.getMessage();
                                    if (token != null) {
                                        // Save token to shared preferences
                                        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("TOKEN", token);
                                        editor.putLong("ExpiredDate", System.currentTimeMillis() + TimeUnit.HOURS.toMillis(46));
                                        editor.apply();
                                        // Navigate to home activity
                                        //Toast.makeText(Login.this, token, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            else {
                                Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                                login.setVisibility(android.view.View.VISIBLE);
                                progressBar.setVisibility(android.view.View.INVISIBLE);
                            }
                        }
                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
        );

    }
}