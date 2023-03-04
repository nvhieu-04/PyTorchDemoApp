package org.pytorch.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import org.pytorch.demo.ui.login.Login;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Splash_screen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //Splash Screen duration
        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SharedPreferences prefs = getSharedPreferences("myKey", Context.MODE_PRIVATE);
                String token = prefs.getString("TOKEN", null);
                if(prefs.getLong("ExpiredDate", -1) > System.currentTimeMillis())
                {
                    if(token != null)
                    {
                        startActivity(new Intent(Splash_screen.this, MainActivity.class));
                        finish();
                    }
                }
                else {
                    SharedPreferences sharedPreferences = getSharedPreferences("myKey", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = user.edit();
                    edit.clear();
                    edit.apply();
                    Toast.makeText(Splash_screen.this, "Đã hết phiên đăng nhập", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Splash_screen.this, Login.class));
                    finish();
                }
            }
        }, secondsDelayed * 2000);
    }

}