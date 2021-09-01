package com.example.jeongstagram.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jeongstagram.R;
import com.example.jeongstagram.activity.MainActivity;
import com.example.jeongstagram.activity.ProfileActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class TutorialFragment extends Fragment {

    private Button buttonTutorialEnd;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private TutorialFragment() {
    }

    public static TutorialFragment newInstance(int page) {
        TutorialFragment tutorialFragment = new TutorialFragment();
        Bundle args = new Bundle();
        args.putInt("tutorial_page", page);
        tutorialFragment.setArguments(args);
        return tutorialFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int page = this.getArguments().getInt("tutorial_page");
        View view = inflater.inflate(page, container, false);

        if (page == R.layout.tutorial_3) {
            buttonTutorialEnd = view.findViewById(R.id.btn_start);
            buttonTutorialEnd.setOnClickListener(v -> {
                storageReference.child("userImages/" + uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (uri == null) {
                            getActivity().startActivity(new Intent(getActivity(), ProfileActivity.class));
                            getActivity().finish();
                        } else {
                            getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getActivity().startActivity(new Intent(getActivity(), ProfileActivity.class));
                        getActivity().finish();
                    }
                });
            });
        }
        return view;
    }
}