package com.example.jeongstagram.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.jeongstagram.BottomSheetSettingFragment;
import com.example.jeongstagram.UserAccount;
import com.example.jeongstagram.databinding.FragmentPhotoBinding;
import com.example.jeongstagram.databinding.FragmentProfileBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.btnSettingMenu.setOnClickListener(v -> {
            BottomSheetSettingFragment sheetDialogFragment = new BottomSheetSettingFragment();
            sheetDialogFragment.show(getActivity().getSupportFragmentManager(), "bottomSheet");
        });

        databaseReference.child("User").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount account = snapshot.getValue(UserAccount.class);
                binding.tvName.setText(account.getName());
                binding.tvIntroduce.setText(account.getIntroduce());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        // Inflate the layout for this fragment
        return view;
    }
}