package com.achievers.ui.achievements;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.R;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.achievements.AchievementsMockDataSource;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.utils.ActivityUtils;

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

        AchievementsFragment view = (AchievementsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (view == null) {
            view = new AchievementsFragment();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    view,
                    R.id.contentFrame);
        }

        AchievementsDataSource dataSource = AchievementsMockDataSource.getInstance();

        view.setViewModel(new AchievementsViewModel());
        view.setPresenter(new AchievementsPresenter(this, view, dataSource));
    }
}