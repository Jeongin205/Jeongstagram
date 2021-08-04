package com.example.jeongstagram;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {
    private List<Integer> layouts = new ArrayList<>();

    public ScreenSlidePagerAdapter(FragmentActivity fragmentActivity, List<Integer> layouts) {
        super(fragmentActivity);
        this.layouts = layouts;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return TutorialFragment.newInstance(layouts.get(position));
    }


    @Override
    public int getItemCount() {
        return layouts.size();
    }
}
