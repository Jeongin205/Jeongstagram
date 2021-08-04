package com.example.jeongstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.jeongstagram.databinding.ActivityJoinBinding;

public class JoinActivity extends AppCompatActivity {
    private ActivityJoinBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}