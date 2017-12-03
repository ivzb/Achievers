package com.achievers.ui.achievement;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.Config;
import com.achievers.R;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.sources.achievements.AchievementsMockDataSource;
import com.achievers.data.sources.evidences.EvidencesMockDataSource;
import com.achievers.ui._base.activities.CollapsingToolbarActivity;
import com.achievers.utils.ActivityUtils;

import static com.achievers.Config.NO_ID;

public class AchievementActivity extends CollapsingToolbarActivity {

    public static final String EXTRA_ACHIEVEMENT_ID = "achievement_id";

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

        long achievementId = getIntent().getLongExtra(EXTRA_ACHIEVEMENT_ID, NO_ID);

        if (achievementId == NO_ID) {
            // todo: redirect to friendly error activity
            throw new IllegalArgumentException();
        }

        initCollapsingToolbar(
                Config.PlaceholderImageResource,
                Config.PlaceholderText);

        AchievementsMockDataSource.getInstance().get(-1L, new GetCallback<Achievement>() {
            @Override
            public void onSuccess(Achievement achievement) {
                if (AchievementActivity.this.isFinishing()) return;

                setCollapsingToolbarImage(achievement.getPictureUri());
                setCollapsingToolbarTitle(achievement.getTitle());
            }

            @Override
            public void onFailure(String message) {
                // todo: show message
            }
        });

        AchievementView view = (AchievementView) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (view == null) {
            view = new AchievementView();

            ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                view,
                R.id.contentFrame);
        }

        view.setViewModel(new AchievementViewModel(achievementId));
        view.setPresenter(new AchievementPresenter(
                this,
                view,
                EvidencesMockDataSource.getInstance()));
    }
}