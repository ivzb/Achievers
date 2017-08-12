package com.achievers.Categories;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.achievers.BaseActivity;
import com.achievers.CategoriesMvpController;
import com.achievers.R;

public class CategoriesActivity extends BaseActivity {

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";
    private static final String CURRENT_CATEGORY_ID_KEY = "CURRENT_CATEGORY_ID_KEY";

    private DrawerLayout mDrawerLayout;

//    private CategoriesPresenter mCategoriesPresenter;
    private CategoriesMvpController categoriesMvpTabletController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_act);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        // Load previously saved state, if available.
        Integer categoryId = null;
        CategoriesFilterType currentFiltering = null;
        if (savedInstanceState != null) {
            currentFiltering =
                    (CategoriesFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            categoryId = savedInstanceState.getInt(CURRENT_CATEGORY_ID_KEY, 0);

            if (categoryId == 0) categoryId = null;
        }

        // Create a CategoriesMvpController every time, even after rotation.
        categoriesMvpTabletController = CategoriesMvpController.createCategoriesView(this, categoryId);

        if (currentFiltering != null) {
            categoriesMvpTabletController.setFiltering(currentFiltering);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY,
                categoriesMvpTabletController.getFiltering());

        int categoryId = 0;
        if (categoriesMvpTabletController.getCategoryId() != null)
            categoryId = categoriesMvpTabletController.getCategoryId();
        outState.putInt(CURRENT_CATEGORY_ID_KEY, categoryId);

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
        if (this.categoriesMvpTabletController.navigateToPreviousCategory()) return;

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