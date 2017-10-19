package com.achievers.ui.achievements.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class AchievementsFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };

    public AchievementsFragmentPagerAdapter(
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

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}