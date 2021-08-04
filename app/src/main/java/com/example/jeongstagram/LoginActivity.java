package com.example.jeongstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.jeongstagram.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.join.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.sliding_up, R.anim.stay);
        });
    }
}