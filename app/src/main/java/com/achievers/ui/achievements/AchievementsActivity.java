package com.achievers.ui.achievements;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.R;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.ui.achievements.adapters.AchievementsFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AchievementsActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.achievements_act);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(AchievementsFragment.newInstance(this));
        fragments.add(AchievementsFragment.newInstance(this));

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new AchievementsFragmentPagerAdapter(getSupportFragmentManager(), fragments));

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}