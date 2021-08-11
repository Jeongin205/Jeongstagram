package com.example.jeongstagram.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.widget.TableLayout;

import com.example.jeongstagram.R;
import com.example.jeongstagram.ViewPagerAdapter;
import com.example.jeongstagram.databinding.ActivityMainBinding;
import com.example.jeongstagram.tutorial.TutorialActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int[] titles = new int[]{R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_photo, R.drawable.ic_shop, R.drawable.ic_user};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Fragment frag1 = new HomeFragment();
        Fragment frag2 = new SearchFragment();
        Fragment frag3 = new PhotoFragment();
        Fragment frag4 = new ShopFragment();
        Fragment frag5 = new ProfileFragment();

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        adapter.addFrag(frag1);
        adapter.addFrag(frag2);
        adapter.addFrag(frag3);
        adapter.addFrag(frag4);
        adapter.addFrag(frag5);

        binding.pager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.pager, (tab, position) -> tab.setIcon(titles[position])).attach();

        SharedPreferences sharedPreferences = getSharedPreferences("checkFirstAccess", Activity.MODE_PRIVATE);
        boolean checkFirstAccess = sharedPreferences.getBoolean("checkFirstAccess", false);

        if (!checkFirstAccess) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("checkFirstAccess", true);
            editor.apply();

            Intent tutorialIntent = new Intent(MainActivity.this, TutorialActivity.class);
            startActivity(tutorialIntent);
            finish();
        }


    }
}