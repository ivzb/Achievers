package com.achievers.ui.achievements;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;

import com.achievers.R;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.ui.achievements.adapters.AchievementsPagerAdapter;
import com.achievers.ui.add_achievement.AddAchievementActivity;

import java.util.ArrayList;
import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

public class AchievementsActivity
        extends AbstractActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.achievements_act);

        FloatingActionButton fab = findViewById(R.id.fabAddAchievement);
        fab.setOnClickListener(this);

        ViewPagerMetadata achievementsMetadata = initAchievementsMetadata();

        ViewPager viewPager = findViewById(R.id.view_pager);
        AchievementsPagerAdapter adapter = new AchievementsPagerAdapter(
                getSupportFragmentManager(),
                achievementsMetadata.getFragments());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        achievementsMetadata.setupTabLayout(this, tabLayout);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AddAchievementActivity.class);
        startActivityForResult(intent, AddAchievementActivity.REQUEST_ADD_ACHIEVEMENT);
    }

    private ViewPagerMetadata initAchievementsMetadata() {
        ViewPagerMetadata achievementsMetadata = new ViewPagerMetadata(3);

        achievementsMetadata.add(
                AchievementsFragment.newInstance(this),
                "Home",
                R.drawable.ic_home);

        achievementsMetadata.add(
                AchievementsFragment.newInstance(this),
                "Trending",
                R.drawable.ic_trending);

        achievementsMetadata.add(
                AchievementsFragment.newInstance(this),
                "Subscriptions",
                R.drawable.ic_subscriptions);

        return achievementsMetadata;
    }

    private class ViewPagerMetadata {

        private int mSize;
        private SparseArray<Fragment> mFragments;
        private SparseArray<String> mTitles;
        private SparseIntArray mIcons;

        ViewPagerMetadata(int initialCapacity) {
            mSize = 0;
            mFragments = new SparseArray<>(initialCapacity);
            mTitles = new SparseArray<>(initialCapacity);
            mIcons = new SparseIntArray(initialCapacity);
        }

        void add(Fragment fragment, String title, int icon) {
            mFragments.put(mSize, fragment);
            mTitles.put(mSize, title);
            mIcons.put(mSize, icon);
            mSize++;
        }

        List<Fragment> getFragments() {
            List<Fragment> fragments = new ArrayList<>(mSize);

            for (int i = 0; i < mSize; i++) {
                fragments.add(mFragments.get(i));
            }

            return fragments;
        }

        void setupTabLayout(Context context, TabLayout tabLayout) {
            checkNotNull(context);

            for (int i = 0; i < mSize; i++) {
                TabLayout.Tab tab = checkNotNull(tabLayout.getTabAt(i));

                tab.setText(mTitles.get(i));
                tab.setIcon(mIcons.get(i));
            }
        }
    }
}