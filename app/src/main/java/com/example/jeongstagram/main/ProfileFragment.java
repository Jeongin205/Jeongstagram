package com.example.jeongstagram.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.jeongstagram.BottomSheetSettingFragment;
import com.example.jeongstagram.databinding.FragmentPhotoBinding;
import com.example.jeongstagram.databinding.FragmentProfileBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.btnSettingMenu.setOnClickListener(v -> {
            BottomSheetSettingFragment sheetDialogFragment = new BottomSheetSettingFragment();
            sheetDialogFragment.show(getActivity().getSupportFragmentManager(), "bottomSheet");
        });
        // Inflate the layout for this fragment
        return view;
    }
}