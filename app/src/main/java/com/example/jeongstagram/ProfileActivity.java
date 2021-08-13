package com.example.jeongstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jeongstagram.databinding.ActivityProfileBinding;
import com.example.jeongstagram.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    private final int GET_GALLERY_IMAGE = 200;
    Uri selectedImageUri;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivUser.setBackground(new ShapeDrawable(new OvalShape()));
        binding.ivUser.setClipToOutline(true);

        binding.ivUser.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, GET_GALLERY_IMAGE);
        });

        binding.btnSave.setOnClickListener(v -> {
            if(selectedImageUri!=null) {
                FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(selectedImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        intent();
                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(), "사진을 넣어주세요", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnSkip.setOnClickListener(v -> {
            FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(Uri.parse("android.resource://com.example.jeongstagram/drawable/ic_account")).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(Task<UploadTask.TaskSnapshot> task) {
                    intent();
                }
            });
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            Glide.with(getApplicationContext()).load(selectedImageUri).into(binding.ivUser);
        }
    }
    private void intent(){
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
    }

}