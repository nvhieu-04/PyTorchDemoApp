package org.pytorch.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.pytorch.demo.databinding.ActivityMainBinding;
import org.pytorch.demo.models.UserInfo;
import org.pytorch.demo.ui.login.ApiClient;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    BottomNavigationView navView = findViewById(R.id.nav_view);
    //change color status bar
    Window window = getWindow();
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.setStatusBarColor(Color.parseColor("#C9E265"));
    //get API store data user information in shared preferences
    SharedPreferences prefs = getSharedPreferences("myKey", Context.MODE_PRIVATE);
    String token = prefs.getString("TOKEN", null);
    if(token != null)
    {
      getInfoUser();
    }
    //Wait for 1s to get data from API
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_camera, R.id.navigation_search, R.id.navigation_profile)
            .build();
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
    //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    NavigationUI.setupWithNavController(binding.navView, navController);
  }
  private void getInfoUser()
  {
    SharedPreferences prefs = getSharedPreferences("myKey", Context.MODE_PRIVATE);
    String token = prefs.getString("TOKEN", null);
    Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
    Call<UserInfo> call = ApiClient.getUserService().me(token);
    call.enqueue(new retrofit2.Callback<UserInfo>() {
      @Override
      public void onResponse(Call<UserInfo> call, retrofit2.Response<UserInfo> response) {
        if (response.isSuccessful()) {
          UserInfo userInfo = response.body();
          SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
          SharedPreferences.Editor editor = sharedPreferences.edit();
          assert userInfo != null;
          editor.putString("name", userInfo.getUsername());
          editor.putString("email", userInfo.getEmail());
          editor.putString("id", userInfo.get_id());
          editor.putString("createAt", userInfo.getCreatedAt());
          editor.apply();
        }
      }

      @Override
      public void onFailure(Call<UserInfo> call, Throwable t) {
        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
      }
    });
  }
}
