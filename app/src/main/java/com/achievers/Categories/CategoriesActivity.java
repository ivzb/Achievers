package com.achievers.Categories;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.achievers.BaseActivity;
import com.achievers.R;
import com.achievers.data.source.CategoriesRepository;
import com.achievers.data.source.local.CategoriesLocalDataSource;
import com.achievers.data.source.remote.CategoriesRemoteDataSource;
import com.achievers.util.ActivityUtils;

public class CategoriesActivity extends BaseActivity {

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private DrawerLayout mDrawerLayout;

    private CategoriesPresenter mCategoriesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_act);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        CategoriesFragment categoriesFragment =
                (CategoriesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (categoriesFragment == null) {
            // Create the fragment
            categoriesFragment = CategoriesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), categoriesFragment, R.id.contentFrame);
        }

        // Instantiate repository
        CategoriesRepository repository = CategoriesRepository.getInstance(
                CategoriesRemoteDataSource.getInstance(),
                CategoriesLocalDataSource.getInstance(super.mRealm));

        // Create the presenter
        mCategoriesPresenter = new CategoriesPresenter(repository, categoriesFragment);

        CategoriesViewModel categoriesViewModel =
                new CategoriesViewModel(getApplicationContext(), mCategoriesPresenter);

        categoriesFragment.setViewModel(categoriesViewModel);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, mCategoriesPresenter.getFiltering());

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                // Do nothing, we're already on that screen
                                break;
                            case R.id.statistics_navigation_menu_item:
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
}