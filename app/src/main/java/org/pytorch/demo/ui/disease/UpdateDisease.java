package org.pytorch.demo.ui.disease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import org.pytorch.demo.R;

public class UpdateDisease extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_disease);
        Intent i = getIntent();
        String name = i.getStringExtra("diseaseName");
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

    }
}