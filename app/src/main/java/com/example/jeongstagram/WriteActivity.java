package com.example.jeongstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.jeongstagram.databinding.ActivityWriteBinding;

public class WriteActivity extends AppCompatActivity {
    ActivityWriteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancel.setOnClickListener(v -> {
            finish();
        });

    }
}