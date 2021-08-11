package com.example.jeongstagram.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.jeongstagram.databinding.FragmentPhotoBinding;
import com.example.jeongstagram.databinding.FragmentSearchBinding;

public class PhotoFragment extends Fragment {
    private FragmentPhotoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhotoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment
        return view;
    }
}