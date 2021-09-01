package com.example.jeongstagram.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeongstagram.activity.SettingActivity;
import com.example.jeongstagram.databinding.FragmentBottomSheetSettingBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheetSettingFragment extends BottomSheetDialogFragment {
    FragmentBottomSheetSettingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBottomSheetSettingBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        binding.userSetting.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
            dismiss();
        });


        // Inflate the layout for this fragment
        return v;
    }
}