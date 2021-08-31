package com.example.jeongstagram.tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    private final int GET_GALLERY_IMAGE = 200;
    Uri selectedImageUri;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.profileImageview.setBackground(new ShapeDrawable(new OvalShape()));
        binding.profileImageview.setClipToOutline(true);

        binding.profileImageview.setOnClickListener(v -> {
            setImage();
        });
        binding.profileTextview.setOnClickListener(v -> {
            setImage();
        });

        binding.saveButton.setOnClickListener(v -> {
            setProgressDialog();
            if (selectedImageUri != null) {
                FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(selectedImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        intent();
                        progressDialog.dismiss();
                    }
                });
            } else {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "사진을 넣어주세요", Toast.LENGTH_SHORT).show();
            }
        });

        binding.skipButton.setOnClickListener(v -> {
            setProgressDialog();
            FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(Uri.parse("android.resource://com.example.jeongstagram/drawable/ic_account")).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(Task<UploadTask.TaskSnapshot> task) {
                    intent();
                    progressDialog.dismiss();
                }
            });
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

    private void intent() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void setProgressDialog(){
        progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("처리중입니다..");
        progressDialog.show();
    }
    private void setImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE);
    }

}