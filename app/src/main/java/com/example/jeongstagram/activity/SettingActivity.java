package com.example.jeongstagram.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jeongstagram.databinding.ActivitySettingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AlertDialog.Builder builder1 = new AlertDialog.Builder(SettingActivity.this);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(SettingActivity.this);
        final EditText editText = new EditText(SettingActivity.this);

        reference.child("User").child(uid).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email = snapshot.getValue(String.class);
                binding.emailTextview.setText(email);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        binding.logout.setOnClickListener(v -> {
            builder1.setTitle("????????????").setMessage("??????????????? ???????????????????").setPositiveButton("???", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }).setNegativeButton("?????????", null).show();
        });

        binding.withdrawal.setOnClickListener(v -> {
            builder1.setTitle("?????? ??????").setMessage("?????? ????????? ???????????????????\n*????????? ??????????????????").setPositiveButton("???", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    builder2.setTitle("?????? ??????").setMessage("????????? " + email + "??? ??????????????????").setView(editText).setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(editText.getText().toString().equals(email)){
                                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            reference.child("User").child(uid).setValue(null);
                                            FirebaseAuth.getInstance().signOut();
                                            Toast.makeText(getApplicationContext(), "?????????????????? ???????????????.", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                        else Toast.makeText(getApplicationContext(), "??????", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else builder1.setTitle("?????? ??????").setMessage("?????? ??????????????????").setPositiveButton("???", null).setNegativeButton("", null).show();
                        }
                    }).setNegativeButton("", null).show();
                }
            }).setNegativeButton("?????????", null).show();
        });

    }
}