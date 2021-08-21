package com.example.jeongstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        final EditText editText = new EditText(SettingActivity.this);

        reference.child("User").child(uid).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email = snapshot.getValue(String.class);
                binding.tvEmail.setText(email);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        binding.logout.setOnClickListener(v -> {
            builder.setTitle("로그아웃").setMessage("로그아웃을 하시겠습니까?").setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }).setNegativeButton("아니요", null).show();
        });

        binding.withdrawal.setOnClickListener(v -> {
            builder.setTitle("계정 탈퇴").setMessage("계정 탈퇴를 하시겠습니까?\n*복구가 불가능합니다").setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    builder.setTitle("계정 탈퇴").setMessage("아래에 " + email + "을 입력해주세요").setView(editText).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(editText.getText().toString().equals(email)){
                                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        reference.child("User").child(uid).setValue(null);
                                        FirebaseAuth.getInstance().signOut();
                                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                            else builder.setTitle("입력오류").setMessage("다시 시도해주세요").setPositiveButton("예", null).show();
                        }
                    }).setNegativeButton("", null).show();


                }
            }).setNegativeButton("아니요", null).show();
        });

    }
}