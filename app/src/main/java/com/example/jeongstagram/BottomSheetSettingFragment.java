package com.example.jeongstagram;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeongstagram.databinding.FragmentBottomSheetSettingBinding;


public class BottomSheetSettingFragment extends Fragment {
    FragmentBottomSheetSettingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBottomSheetSettingBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        // Inflate the layout for this fragment
        return v;
    }
}