package org.pytorch.demo.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.pytorch.demo.MainActivity;
import org.pytorch.demo.R;
import org.pytorch.demo.ui.login.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    EditText email, password, confirmPassword, name;
    Button register;
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.editTextTextEmailAddressRegister);
        password = findViewById(R.id.editTextTextPasswordRegister);
        confirmPassword = findViewById(R.id.editTextTextPasswordAgainRegister);
        name = findViewById(R.id.editTextTextUserName);
        register = findViewById(R.id.buttonRegister);
        login = findViewById(R.id.textViewtoLogin);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#DEEAD8"));
        ImageView backRegister = findViewById(R.id.back_Register);
        backRegister.setOnClickListener(v -> {
            onBackPressed();
        });
        register.setOnClickListener(
                v -> {
                    String email = this.email.getText().toString();
                    String password = this.password.getText().toString();
                    String confirmPassword = this.confirmPassword.getText().toString();
                    String name = this.name.getText().toString();
                    if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty()) {
                        return;
                    }
                    RegisterRequest registerRequest = new RegisterRequest(name,email, password);
                    Call<RegisterResponse> call = ApiClient.getUserService().register(registerRequest);
                    call.enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            if (response.isSuccessful()) {
                                RegisterResponse registerResponse = response.body();
                                if (registerResponse != null) {
                                    String token = registerResponse.getToken();
                                    String message = registerResponse.getMessage();
                                    if (token != null) {
                                        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("TOKEN", token);
                                        editor.apply();
                                        //Toast.makeText(Register.this, token, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Register.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
        );

    }
}