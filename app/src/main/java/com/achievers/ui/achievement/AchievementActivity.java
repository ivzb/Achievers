package com.achievers.ui.achievement;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.R;
import com.achievers.data.source.achievements.AchievementsRemoteDataSource;
import com.achievers.data.source.evidence.EvidenceRemoteDataSource;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.utils.ActivityUtils;

public class AchievementActivity extends AbstractActivity {

    public static final String EXTRA_ACHIEVEMENT_ID = "ACHIEVEMENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.achievement_detail_act);

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
            int achievementId = getIntent().getIntExtra(EXTRA_ACHIEVEMENT_ID, 0);
            view = AchievementFragment.newInstance(achievementId);

            AchievementPresenter presenter = new AchievementPresenter(
                    achievementId,
                    view,
                    AchievementsRemoteDataSource.getInstance(),
                    EvidenceRemoteDataSource.getInstance());

            view.setViewModel(new AchievementViewModel());
            view.setPresenter(presenter);

            ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                view,
                R.id.contentFrame);
        }
    }
}