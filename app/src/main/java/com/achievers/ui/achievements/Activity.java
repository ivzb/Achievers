package com.achievers.ui.achievements;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.data.source.achievements.AchievementsMockDataSource;
import com.achievers.ui.base.BaseActivity;
import com.achievers.R;
import com.achievers.utils.ActivityUtils;

public class Activity extends BaseActivity {

//    public static final String EXTRA_CATEGORY_ID = "CATEGORY_ID";

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

//        int categoryId = getIntent().getIntExtra(EXTRA_CATEGORY_ID, 0);

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

        Contracts.Presenter presenter = new Presenter(
                AchievementsMockDataSource.getInstance(),
                view);

        view.setPresenter(presenter);
        view.setViewModel(
                new ViewModel(this));
    }
}