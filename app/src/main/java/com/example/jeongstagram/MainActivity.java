package com.example.jeongstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.jeongstagram.databinding.ActivityMainBinding;
import com.example.jeongstagram.tutorial.TutorialActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("checkFirstAccess", Activity.MODE_PRIVATE);
        boolean checkFirstAccess = sharedPreferences.getBoolean("checkFirstAccess", false);

        if (!checkFirstAccess) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("checkFirstAccess", true);
            editor.apply();

            Intent tutorialIntent = new Intent(MainActivity.this, TutorialActivity.class);
            startActivity(tutorialIntent);
            finish();
        }


    }
}