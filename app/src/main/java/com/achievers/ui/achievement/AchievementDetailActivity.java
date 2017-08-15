package com.achievers.ui.achievement;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.achievers.BaseActivity;
import com.achievers.R;
import com.achievers.data.source.achievements.AchievementsRemoteDataSource;
import com.achievers.data.source.evidence.EvidenceRemoteDataSource;
import com.achievers.util.ActivityUtils;

/**
 * Displays achievement details screen.
 */
public class AchievementDetailActivity extends BaseActivity {

    public static final String EXTRA_ACHIEVEMENT_ID = "ACHIEVEMENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.achievement_detail_act);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        // Get the requested achievement id
        int achievementId = getIntent().getIntExtra(EXTRA_ACHIEVEMENT_ID, 0);

        AchievementDetailFragment achievementDetailFragment = (AchievementDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (achievementDetailFragment == null) {
            achievementDetailFragment = AchievementDetailFragment.newInstance(achievementId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    achievementDetailFragment, R.id.contentFrame);
        }

        AchievementDetailPresenter presenter = new AchievementDetailPresenter(
                achievementId,
                AchievementsRemoteDataSource.getInstance(),
                EvidenceRemoteDataSource.getInstance(),
                achievementDetailFragment);

        AchievementDetailViewModel viewModel = new AchievementDetailViewModel(getApplicationContext(), presenter);
        achievementDetailFragment.setViewModel(viewModel);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}