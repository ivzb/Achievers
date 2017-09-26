package com.achievers.ui.achievements;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.achievers.ui.base.BaseActivity;
import com.achievers.R;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.entities.Category;
import com.achievers.data.source.achievements.AchievementsRemoteDataSource;
import com.achievers.data.source.categories.remote.CategoriesRemoteDataSource;
import com.achievers.util.ActivityUtils;

/**
 * Displays achievements list screen.
 */
public class AchievementsActivity extends BaseActivity {

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

        AchievementsFragment fragment = (AchievementsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            fragment = new AchievementsFragment();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    fragment,
                    R.id.contentFrame);
        }

        final AchievementsFragment achievementsFragment = fragment;

        final AchievementsPresenter presenter = new AchievementsPresenter(
                AchievementsRemoteDataSource.getInstance(),
                achievementsFragment);

        achievementsFragment.setPresenter(presenter);
        final AchievementsViewModel viewModel = new AchievementsViewModel(getApplicationContext());

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