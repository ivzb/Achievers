package com.achievers.ui.achievements.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class AchievementsPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public AchievementsPagerAdapter(
            FragmentManager fm,
            List<Fragment> fragments) {

        super(fm);
        mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}