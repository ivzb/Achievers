package com.achievers.ui.add_achievement;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.R;
import com.achievers.data.source.involvements.InvolvementsMockDataSource;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.utils.ActivityUtils;

public class AddAchievementActivity extends AbstractActivity {

    public static final int REQUEST_ADD_ACHIEVEMENT = 156;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_achievement_act);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.add_achievement);
        }

        AddAchievementView view =
                (AddAchievementView) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (view == null) {
            view = new AddAchievementView();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    view,
                    R.id.contentFrame);
        }

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            view.setArguments(extras);
        }

        view.setViewModel(new AddAchievementViewModel());
        view.setPresenter(new AddAchievementPresenter(
                this,
                view,
                InvolvementsMockDataSource.getInstance()));
    }
}