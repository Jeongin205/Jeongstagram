package com.example.jeongstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.jeongstagram.databinding.ActivityJoinBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {
    private ActivityJoinBinding binding;
    private FirebaseAuth mAuth;
    boolean isName = false;
    boolean isEmail = false;
    boolean isPwd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        ProgressDialog progressDialog = new ProgressDialog(JoinActivity.this);
        mAuth = FirebaseAuth.getInstance();

        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) isName = true;
                else isName = false;
            }
        });
        binding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    binding.tvErrorEmail.setText("이메일 형식으로 입력해주세요.");
                    binding.etEmail.setBackgroundResource(R.drawable.red_edittext);
                } else {
                    binding.tvErrorEmail.setText("");
                    binding.etEmail.setBackgroundResource(R.drawable.edit);
                    if (s != null) isEmail = true;
                    else isEmail = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Pattern.matches("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{8,15}$", s)) {
                    binding.tvErrorPwd.setText("숫자, 문자, 특수문자중 2가지를 포함하세요");
                    binding.etPwd.setBackgroundResource(R.drawable.red_edittext);
                } else {
                    binding.tvErrorPwd.setText("");
                    binding.etPwd.setBackgroundResource(R.drawable.edit);
                    binding.etPwdch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String p1 = binding.etPwd.getText().toString();
                            String p2 = binding.etPwdch.getText().toString();
                            if (!p2.equals(p1)) {
                                binding.tvErrorPwdch.setText("비밀번호와 일치하지 않습니다.");
                                binding.etPwdch.setBackgroundResource(R.drawable.red_edittext);
                            } else {
                                binding.tvErrorPwdch.setText("");
                                binding.etPwdch.setBackgroundResource(R.drawable.edit);
                                if (binding.etPwdch.getText().toString() != null) isPwd = true;
                                else isPwd = false;
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.join.setOnClickListener(v -> {
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("처리중입니다..");
            progressDialog.show();
            if (isName && isEmail && isPwd) {
                String name = binding.etName.getText().toString();
                String email = binding.etEmail.getText().toString();
                String pwd = binding.etPwd.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = user.getUid();
                            UserAccount account = new UserAccount(name, email, uid);
                            databaseReference.child("User").child(uid).setValue(account);
                            progressDialog.dismiss();
                            Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(JoinActivity.this, "등록 에러", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "정보를 확인해주세요", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.stay, R.anim.sliding_down);
    }
}