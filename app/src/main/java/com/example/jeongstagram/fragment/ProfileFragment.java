package com.example.jeongstagram.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.jeongstagram.activity.EditProfileActivity;
import com.example.jeongstagram.data.UserData;
import com.example.jeongstagram.activity.WriteActivity;
import com.example.jeongstagram.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.writeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WriteActivity.class);
            startActivity(intent);
        });
        binding.bottomSheetMenuButton.setOnClickListener(v -> {
            BottomSheetSettingFragment sheetDialogFragment = new BottomSheetSettingFragment();
            sheetDialogFragment.show(getActivity().getSupportFragmentManager(), "bottomSheet");
        });

        databaseReference.child("User").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserData account = snapshot.getValue(UserData.class);
                binding.nameTextview.setText(account.getName());
                if(account.getIntroduce()==null) binding.introduceTextview.setText("");
                else binding.introduceTextview.setText(account.getIntroduce());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        storageReference.child("userImages/"+uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ProfileFragment.this).load(uri).into(binding.profileImageview);
            }
        });
        binding.profileEditButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });
        return view;
    }
}