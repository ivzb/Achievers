package com.achievers.ui.achievement;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.achievers.R;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.achievements.AchievementsMockDataSource;
import com.achievers.data.source.evidence.EvidenceRemoteDataSource;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.utils.ActivityUtils;
import com.facebook.drawee.view.SimpleDraweeView;

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
        AchievementsDataSource achievementsDataSource = AchievementsMockDataSource.getInstance();

        initCollapsingToolbar(achievementId, achievementsDataSource);

        view.setViewModel(new AchievementViewModel());
        view.setPresenter(new AchievementPresenter(
                achievementId,
                view,
                achievementsDataSource,
                EvidenceRemoteDataSource.getInstance()));
    }

    private void initCollapsingToolbar(long achievementId, AchievementsDataSource achievementsDataSource) {
        // todo: add default achievement image while loading

        achievementsDataSource.getAchievement(achievementId, new GetCallback<Achievement>() {
            @Override
            public void onSuccess(Achievement achievement) {
                SimpleDraweeView image = findViewById(R.id.image);
                Uri uri = Uri.parse(achievement.getPictureUrl());
                image.setImageURI(uri);

                CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
                collapsingToolbarLayout.setTitle(achievement.getTitle());
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(AchievementActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}