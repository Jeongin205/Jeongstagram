package com.example.jeongstagram.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.jeongstagram.R;
import com.example.jeongstagram.data.UserData;
import com.example.jeongstagram.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    boolean isName = false;
    boolean isEmail = false;
    boolean isPwd = false;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        mAuth = FirebaseAuth.getInstance();
        editor = getSharedPreferences("user", MODE_PRIVATE).edit();

        binding.nameEdittext.addTextChangedListener(new TextWatcher() {
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
        binding.emailEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    binding.emailErrorTextview.setText("????????? ???????????? ??????????????????.");
                    binding.emailEdittext.setBackgroundResource(R.drawable.red_edittext);
                } else {
                    binding.emailErrorTextview.setText("");
                    binding.emailEdittext.setBackgroundResource(R.drawable.edit);
                    if (s != null) isEmail = true;
                    else isEmail = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.passwordEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Pattern.matches("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{8,15}$", s)) {
                    binding.passwordErrorTextview.setText("??????, ??????, ??????????????? 2????????? ???????????????");
                    binding.passwordEdittext.setBackgroundResource(R.drawable.red_edittext);
                } else {
                    binding.passwordErrorTextview.setText("");
                    binding.passwordEdittext.setBackgroundResource(R.drawable.edit);
                    binding.passwordCheckEdittext.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String p1 = binding.passwordEdittext.getText().toString();
                            String p2 = binding.passwordCheckEdittext.getText().toString();
                            if (!p2.equals(p1)) {
                                binding.passwordCheckErrorTextview.setText("??????????????? ???????????? ????????????.");
                                binding.passwordCheckEdittext.setBackgroundResource(R.drawable.red_edittext);
                            } else {
                                binding.passwordCheckErrorTextview.setText("");
                                binding.passwordCheckEdittext.setBackgroundResource(R.drawable.edit);
                                if (binding.passwordCheckEdittext.getText().toString() != null) isPwd = true;
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
        binding.signUpButton.setOnClickListener(v -> {
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("??????????????????..");
            progressDialog.show();
            if (isName && isEmail && isPwd) {
                String name = binding.nameEdittext.getText().toString();
                String email = binding.emailEdittext.getText().toString();
                String pwd = binding.passwordEdittext.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = user.getUid();
                            String introduce = "???????????????";
                            UserData account = new UserData(name, email, uid, introduce);
                            databaseReference.child("User").child(uid).setValue(account);
                            editor.putString("name", name);
                            editor.apply();
                            progressDialog.dismiss();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "?????? ??????", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "????????? ??????????????????", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.stay, R.anim.sliding_down);
    }
}