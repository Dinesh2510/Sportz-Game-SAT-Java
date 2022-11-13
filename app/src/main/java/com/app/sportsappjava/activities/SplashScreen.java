package com.app.sportsappjava.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.sportsappjava.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {
    ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handleScreen();
    }

    private void handleScreen() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            finish();
            startActivity(i);

        }, 2000);

    }

}