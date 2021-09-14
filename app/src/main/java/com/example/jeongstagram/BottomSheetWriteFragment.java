package com.example.jeongstagram;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeongstagram.databinding.FragmentBottomSheetWriteBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheetWriteFragment extends BottomSheetDialogFragment {
    FragmentBottomSheetWriteBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBottomSheetWriteBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        return v;
    }
}