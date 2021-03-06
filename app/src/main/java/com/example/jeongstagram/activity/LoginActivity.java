package com.example.jeongstagram.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jeongstagram.R;
import com.example.jeongstagram.data.UserData;
import com.example.jeongstagram.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    String name, email;
    boolean isEmail = false;
    boolean isPwd = false;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    ProgressDialog progressDialog;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        Log.d("LoginActivity", mAuth.getUid() + "");
        editor = getSharedPreferences("user", MODE_PRIVATE).edit();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);

        if (mAuth.getCurrentUser() != null) {
            Log.d("LoginActivity", mAuth.getCurrentUser().getUid());
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        binding.googleLoginButton.setOnClickListener(v -> {
            setProgressDialog();
            signIn();
        });

        binding.signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.sliding_up, R.anim.stay);
        });

        binding.loginButton.setOnClickListener(v -> {
            setProgressDialog();
            if (binding.emailEdittext.getText().toString().replace(" ", "").equals(""))
                isEmail = false;
            else isEmail = true;
            if (binding.passwordEdittext.getText().toString().replace(" ", "").equals(""))
                isPwd = false;
            else isPwd = true;
            if (isEmail && isPwd) {
                String email = binding.emailEdittext.getText().toString();
                String pwd = binding.passwordEdittext.getText().toString();
                mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "???????????????", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
            } else {
                if (!isEmail && !isPwd)
                    Toast.makeText(getApplicationContext(), "????????? ??????????????????", Toast.LENGTH_SHORT).show();
                else if (!isEmail)
                    Toast.makeText(getApplicationContext(), "???????????? ??????????????????", Toast.LENGTH_SHORT).show();
                else if (!isPwd)
                    Toast.makeText(getApplicationContext(), "??????????????? ??????????????????", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            Log.d("asdf", "??????");
                            databaseReference.child("User").child(uid).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.getValue() == null) {
                                        email = user.getEmail();
                                        name = user.getDisplayName();
                                        String introduce = "???????????????";
                                        UserData account = new UserData(name, email, uid, introduce);
                                        databaseReference.child("User").child(uid).setValue(account);
                                        progressDialog.dismiss();
                                    }
                                    editor.putString("name", name);
                                    editor.apply();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                            updateUI(user);
                        } else {
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setProgressDialog() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("?????????????????????");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}