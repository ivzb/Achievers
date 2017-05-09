package com.achievers.Achievements;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.achievers.BaseActivity;
import com.achievers.R;
import com.achievers.data.Category;
import com.achievers.data.source.AchievementsRepository;
import com.achievers.data.source.CategoriesDataSource;
import com.achievers.data.source.CategoriesRepository;
import com.achievers.data.source.local.AchievementsLocalDataSource;
import com.achievers.data.source.local.CategoriesLocalDataSource;
import com.achievers.data.source.remote.AchievementsRemoteDataSource;
import com.achievers.data.source.remote.CategoriesRemoteDataSource;
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

        AchievementsFragment fragment = (AchievementsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            fragment = AchievementsFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.contentFrame);
        }

        // Instantiate repositories
        AchievementsRepository achievementsRepository = AchievementsRepository.getInstance(
                AchievementsRemoteDataSource.getInstance(),
                AchievementsLocalDataSource.getInstance(super.mRealm));

        CategoriesRepository categoriesRepository = CategoriesRepository.getInstance(
                CategoriesRemoteDataSource.getInstance(),
                CategoriesLocalDataSource.getInstance(super.mRealm));

        final AchievementsFragment achievementsFragment = fragment;

        final AchievementsPresenter presenter = new AchievementsPresenter(achievementsRepository, achievementsFragment);
        achievementsFragment.setPresenter(presenter);
        final AchievementsViewModel viewModel = new AchievementsViewModel(getApplicationContext());

        categoriesRepository.getCategory(categoryId, new CategoriesDataSource.GetCategoryCallback() {
            @Override
            public void onLoaded(Category category) {
                viewModel.setCategory(category);
                achievementsFragment.setViewModel(viewModel);
            }

            @Override
            public void onDataNotAvailable() {
                // todo: show error in result?
                //presenter.result();
            }
        });

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