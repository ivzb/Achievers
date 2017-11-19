package com.achievers.ui.home.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class HomeAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;

    public HomeAdapter(
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