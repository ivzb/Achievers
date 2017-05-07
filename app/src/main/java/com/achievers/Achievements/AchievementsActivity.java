package com.achievers.Achievements;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.achievers.BaseActivity;
import com.achievers.R;
import com.achievers.data.Evidence;
import com.achievers.data.source.AchievementsRepository;
import com.achievers.data.source.EvidenceRepository;
import com.achievers.data.source.local.AchievementsLocalDataSource;
import com.achievers.data.source.local.EvidenceLocalDataSource;
import com.achievers.data.source.remote.AchievementsRemoteDataSource;
import com.achievers.data.source.remote.EvidenceRemoteDataSource;
import com.achievers.util.ActivityUtils;

/**
 * Displays achievements list screen.
 */
public class AchievementsActivity extends BaseActivity {

    public static final String EXTRA_CATEGORY_ID = "CATEGORY_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.achievements_act);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        // Get the requested category id
        int categoryId = getIntent().getIntExtra(EXTRA_CATEGORY_ID, 0);

        AchievementsFragment achievementsFragment = (AchievementsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (achievementsFragment == null) {
            achievementsFragment = AchievementsFragment.newInstance(categoryId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    achievementsFragment, R.id.contentFrame);
        }

        // Instantiate repositories
        AchievementsRepository achievementsRepository = AchievementsRepository.getInstance(
                AchievementsRemoteDataSource.getInstance(),
                AchievementsLocalDataSource.getInstance(super.mRealm));

        AchievementsPresenter presenter = new AchievementsPresenter(achievementsRepository, achievementsFragment);

        achievementsFragment.setPresenter(presenter);

        AchievementsViewModel viewModel = new AchievementsViewModel(getApplicationContext());

        // todo: get category from repo and pass it to viewModel
        viewModel.setCategory();

        achievementsFragment.setViewModel(viewModel);
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