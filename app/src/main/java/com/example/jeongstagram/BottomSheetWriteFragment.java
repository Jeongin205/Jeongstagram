package com.example.jeongstagram;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeongstagram.databinding.FragmentBottomSheetWriteBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheetWriteFragment extends BottomSheetDialogFragment {
    FragmentBottomSheetWriteBinding binding;
    private BottomSheetListener mListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBottomSheetWriteBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        mListener = (BottomSheetListener)getContext();
        binding.gallery.setOnClickListener(view -> {
            mListener.onClickGallery();
        });
        binding.camera.setOnClickListener(view -> {
            mListener.onCLickCamera();
        });
        return v;
    }
    public interface BottomSheetListener{
        void onClickGallery();
        void onCLickCamera();
    }
}