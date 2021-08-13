package com.example.jeongstagram.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jeongstagram.ProfileActivity;
import com.example.jeongstagram.main.MainActivity;
import com.example.jeongstagram.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TutorialFragment extends Fragment {

    private Button buttonTutorialEnd;

    private TutorialFragment() {}

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
            buttonTutorialEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        getActivity().startActivity(new Intent(getActivity(), ProfileActivity.class));
                        getActivity().finish();
                    }
                }
            );
        }
        return view;

    }
}