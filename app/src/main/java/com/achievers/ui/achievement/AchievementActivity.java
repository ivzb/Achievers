package com.achievers.ui.achievement;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.R;
import com.achievers.data.source.achievements.AchievementsMockDataSource;
import com.achievers.data.source.achievements.AchievementsRemoteDataSource;
import com.achievers.data.source.evidence.EvidenceRemoteDataSource;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.utils.ActivityUtils;

import static com.achievers.Config.NO_ID;

public class AchievementActivity extends AbstractActivity {

    public static final String EXTRA_ACHIEVEMENT_ID = "ACHIEVEMENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.achievement_act);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        AchievementFragment view = (AchievementFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (view == null) {
            view = new AchievementFragment();

            ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                view,
                R.id.contentFrame);
        }

        long achievementId = getIntent().getLongExtra(EXTRA_ACHIEVEMENT_ID, NO_ID);
        AchievementPresenter presenter = new AchievementPresenter(
                achievementId,
                view,
                AchievementsMockDataSource.getInstance(),
                EvidenceRemoteDataSource.getInstance());

        view.setViewModel(new AchievementViewModel());
        view.setPresenter(presenter);
    }
}