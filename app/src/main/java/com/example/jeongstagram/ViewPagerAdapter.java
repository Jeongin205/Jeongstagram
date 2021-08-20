package com.example.jeongstagram;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jeongstagram.main.HomeFragment;
import com.example.jeongstagram.main.MainActivity;
import com.example.jeongstagram.main.PhotoFragment;
import com.example.jeongstagram.main.ProfileFragment;
import com.example.jeongstagram.main.SearchFragment;
import com.example.jeongstagram.main.ShopFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private final List<Fragment> list = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void addFrag(Fragment fragment) {
        list.add(fragment);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
