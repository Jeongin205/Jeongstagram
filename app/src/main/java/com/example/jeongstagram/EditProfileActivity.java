package com.example.jeongstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jeongstagram.data.UserData;
import com.example.jeongstagram.databinding.ActivityEditProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Uri selectedImageUri;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(uid);
    boolean isName = false;
    private final int GET_GALLERY_IMAGE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cancelButton.setOnClickListener(v -> {
            finish();
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserData account = snapshot.getValue(UserData.class);
                binding.nameEdittext.setText(account.getName());
                binding.introduceEdittext.setText(account.getIntroduce());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        storageReference.child("userImages/"+uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(EditProfileActivity.this).load(uri).into(binding.profileImageview);
            }
        });

        binding.profileImageview.setOnClickListener(v -> {
            setImage();
        });
        binding.profileTextview.setOnClickListener(v -> {
            setImage();
        });

        binding.nameEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(binding.nameEdittext.getText().toString().replace(" ", "").equals("")){
                    binding.nameErrorTextview.setVisibility(View.VISIBLE);
                    isName = false;
                }
                else{
                    binding.nameErrorTextview.setVisibility(View.INVISIBLE);
                    isName = true;
                }
            }
        });

        binding.editButton.setOnClickListener(v -> {
            if(isName){
                if (selectedImageUri != null) {
                    FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(selectedImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {}
                    });
                }
                String name = binding.nameEdittext.getText().toString();
                String introduce = binding.introduceEdittext.getText().toString();
                databaseReference.child("name").setValue(name);
                databaseReference.child("introduce").setValue(introduce);
                finish();
            }
            else Toast.makeText(getApplicationContext(), "이름을 넣어주세요", Toast.LENGTH_SHORT).show();
        });

        binding.userSetting.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfileActivity.this, SettingActivity.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            Glide.with(getApplicationContext()).load(selectedImageUri).into(binding.profileImageview);
        }
    }
    private void setImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE);
    }
}