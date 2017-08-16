package com.achievers.ui.achievements;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.achievers.ui.base.BaseActivity;
import com.achievers.R;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.models.Category;
import com.achievers.data.source.achievements.AchievementsRemoteDataSource;
import com.achievers.data.source.categories.CategoriesRemoteDataSource;
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

        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        // Get the requested category id
        int categoryId = getIntent().getIntExtra(EXTRA_CATEGORY_ID, 0);

        AchievementsFragment fragment = (AchievementsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            fragment = AchievementsFragment.newInstance();

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

        CategoriesRemoteDataSource.getInstance()
                .getCategory(categoryId, new GetCallback<Category>() {
            @Override
            public void onSuccess(final Category category) {
                viewModel.setCategory(category);
                achievementsFragment.setViewModel(viewModel);

                presenter.loadAchievements(category, true);
            }

            @Override
            public void onFailure(String message) {
                // todo: show error in category result?
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