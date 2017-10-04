package com.achievers.ui.achievements;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.R;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.achievements.AchievementsMockDataSource;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.utils.ActivityUtils;

public class Activity extends AbstractActivity {

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

        Fragment fragment = (Fragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            initFragment();
        }
    }

    private void initFragment() {
        Fragment view = new Fragment();

        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                view,
                R.id.contentFrame);

        AchievementsDataSource dataSource = AchievementsMockDataSource.getInstance();

        view.setViewModel(new ViewModel());
        view.setPresenter(new Presenter(this, view, dataSource));
    }
}