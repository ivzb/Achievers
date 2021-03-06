package com.achievers.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.achievers.R;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.ui.achievements.AchievementsView;
import com.achievers.ui.add_achievement.AddAchievementActivity;
import com.achievers.ui.contributions.ContributionsView;
import com.achievers.ui.home.adapters.HomeAdapter;
import com.achievers.ui.quests.QuestsView;

import java.util.ArrayList;
import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

public class HomeActivity
        extends AbstractActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_act);

        FloatingActionButton fab = findViewById(R.id.fabAddAchievement);
        fab.setOnClickListener(this);

        ViewPagerMetadata achievementsMetadata = initAchievementsMetadata();

        ViewPager viewPager = findViewById(R.id.view_pager);

        HomeAdapter adapter = new HomeAdapter(
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
                new AchievementsView(),
                "Home",
                R.drawable.ic_home);

        achievementsMetadata.add(
                new QuestsView(),
                "Quests",
                R.drawable.ic_subscriptions);

        achievementsMetadata.add(
                new ContributionsView(),
                "Contributions",
                R.drawable.ic_trending);

        return achievementsMetadata;
    }

    private class ViewPagerMetadata {

        private List<Fragment> mFragments;
        private List<String> mTitles;
        private List<Integer> mIcons;

        ViewPagerMetadata(int initialCapacity) {
            mFragments = new ArrayList<>(initialCapacity);
            mTitles = new ArrayList<>(initialCapacity);
            mIcons = new ArrayList<>(initialCapacity);
        }

        void add(Fragment fragment, String title, int icon) {
            mFragments.add(fragment);
            mTitles.add(title);
            mIcons.add(icon);
        }

        List<Fragment> getFragments() {
            return mFragments;
        }

        void setupTabLayout(Context context, TabLayout tabLayout) {
            checkNotNull(context);

            for (int i = 0; i < mFragments.size(); i++) {
                TabLayout.Tab tab = checkNotNull(tabLayout.getTabAt(i));

                tab.setText(mTitles.get(i));
                tab.setIcon(mIcons.get(i));
            }
        }
    }
}