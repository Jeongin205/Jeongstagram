package com.example.jeongstagram;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeongstagram.databinding.FragmentBottomSheetSettingBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheetSettingFragment extends BottomSheetDialogFragment {
    FragmentBottomSheetSettingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBottomSheetSettingBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        binding.btnSetting.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
            dismiss();
        });


        // Inflate the layout for this fragment
        return v;
    }
}