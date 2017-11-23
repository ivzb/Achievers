package com.achievers.ui.achievement;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.R;
import com.achievers.data.entities.Achievement;
import com.achievers.data.sources.evidences.EvidencesMockDataSource;
import com.achievers.ui._base.activities.CollapsingToolbarActivity;
import com.achievers.utils.ActivityUtils;

import org.parceler.Parcels;

public class AchievementActivity extends CollapsingToolbarActivity {

    public static final String EXTRA_ACHIEVEMENT = "achievement";

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

        Achievement achievement = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_ACHIEVEMENT));

        initCollapsingToolbar(achievement.getPictureUri(), achievement.getTitle());

        AchievementView view = (AchievementView) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (view == null) {
            view = new AchievementView();

            ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                view,
                R.id.contentFrame);
        }

        view.setViewModel(new AchievementViewModel(achievement));
        view.setPresenter(new AchievementPresenter(
                this,
                view,
                EvidencesMockDataSource.getInstance()));
    }
}