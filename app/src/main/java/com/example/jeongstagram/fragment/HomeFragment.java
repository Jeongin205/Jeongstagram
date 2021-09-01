package com.example.jeongstagram.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeongstagram.activity.WriteActivity;
import com.example.jeongstagram.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.writeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WriteActivity.class);
            startActivity(intent);
        });
        // Inflate the layout for this fragment
        return view;
    }
}