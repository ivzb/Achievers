package com.achievers.ui.categories;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.achievers.R;
import com.achievers.data.sources.categories.CategoriesDataSource;
import com.achievers.data.sources.categories.CategoriesLoaderProvider;
import com.achievers.data.sources.categories.CategoriesRepository;
import com.achievers.data.sources.categories.local.CategoriesLocalDataSource;
import com.achievers.data.sources.categories.remote.CategoriesMockDataSource;
import com.achievers.databinding.CategoriesActBinding;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.utils.ActivityUtils;

public class CategoriesActivity extends AbstractActivity {

//    private static final String CURRENT_CATEGORY_ID_KEY = "CURRENT_CATEGORY_ID_KEY";

    private DrawerLayout mDrawerLayout;
    private CategoriesPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_act);

        CategoriesActBinding binding = DataBindingUtil.setContentView(this, R.layout.categories_act);

        Uri uri = Uri.parse("http://www.webpage-maker.com/guide/images/200821911435669_2.jpg");
        binding.sdvCategory.setImageURI(uri);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        String itemTitle = "Category title";
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(itemTitle);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.dark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        // todo
//        // Load previously saved state, if available.
//        Integer categoryId = null;
//        if (savedInstanceState != null) {
//            categoryId = savedInstanceState.getInt(CURRENT_CATEGORY_ID_KEY, 0);
//
//            if (categoryId == 0) categoryId = null;
//        }

        int fragmentId = R.id.contentFrame;

        CategoriesView categoriesFragment =
                (CategoriesView) getSupportFragmentManager().findFragmentById(fragmentId);

        if (categoriesFragment == null) {
            // Create the fragment
            categoriesFragment = new CategoriesView();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), categoriesFragment, fragmentId);
        }

        // presenter

        CategoriesLoaderProvider loaderProvider = new CategoriesLoaderProvider(this);

        CategoriesDataSource repository = CategoriesRepository.getInstance(
                CategoriesMockDataSource.getInstance(),
                CategoriesLocalDataSource.getInstance(getContentResolver()));

        mPresenter = new CategoriesPresenter(
//                this,
//                getSupportLoaderManager(),
//                loaderProvider,
                categoriesFragment,
                repository);

        CategoriesViewModel categoriesViewModel = new CategoriesViewModel(this);
        categoriesFragment.setViewModel(categoriesViewModel);

        // Create a CategoriesMvpController every time, even after rotation.
        categoriesFragment.setPresenter(mPresenter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // todo
//        int categoryId = 0;
//        if (categoriesMvpTabletController.getCategoryId() != null)
//            categoryId = categoriesMvpTabletController.getCategoryId();
//        outState.putInt(CURRENT_CATEGORY_ID_KEY, categoryId);

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

        return false;
    }

    @Override
    public void onBackPressed() {
        if (this.mPresenter.navigateToPreviousCategory()) return;

        super.onBackPressed();
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