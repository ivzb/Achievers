package com.achievers.ui.addachievement;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.achievers.ui.base.BaseActivity;
import com.achievers.R;
import com.achievers.data.source.achievements.AchievementsRemoteDataSource;
import com.achievers.data.source.files.FilesRemoteDataSource;
import com.achievers.util.ActivityUtils;

/**
 * Displays an add Achievement screen.
 */
public class AddAchievementActivity extends BaseActivity {

    public static final String EXTRA_CATEGORY_ID = "CATEGORY_ID";
    public static final int REQUEST_ADD_ACHIEVEMENT = 156;

    private AddAchievementContract.Presenter mAddAchievementPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_achievement_act);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        AddAchievementFragment addAchievementFragment =
                (AddAchievementFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (addAchievementFragment == null) {
            addAchievementFragment = AddAchievementFragment.newInstance();

            actionBar.setTitle(R.string.add_achievement);

            Bundle fragmentBundle = getIntent().getExtras();
            addAchievementFragment.setArguments(fragmentBundle);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addAchievementFragment, R.id.contentFrame);
        }

        // Create the presenter
        mAddAchievementPresenter = new AddAchievementPresenter(
                AchievementsRemoteDataSource.getInstance(),
                FilesRemoteDataSource.getInstance(),
                addAchievementFragment
        );

        addAchievementFragment.setPresenter(mAddAchievementPresenter);

        AddAchievementViewModel viewModel = new AddAchievementViewModel(getApplicationContext(), mAddAchievementPresenter);
        addAchievementFragment.setViewModel(viewModel);
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