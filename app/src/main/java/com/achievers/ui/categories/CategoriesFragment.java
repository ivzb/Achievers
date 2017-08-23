package com.achievers.ui.categories;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.entities.Category;
import com.achievers.databinding.CategoriesFragBinding;
import com.achievers.util.CursorUtils;
import com.achievers.util.ScrollChildSwipeRefreshLayout;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Display a screen with categories
 */
public class CategoriesFragment extends Fragment implements CategoriesContract.View {

    private CategoriesContract.Presenter mPresenter;
    private CategoriesFragBinding mViewDataBinding;
    private CategoriesViewModel mCategoriesViewModel;

    public CategoriesFragment() {
        // Requires empty public constructor
    }

    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
    }

    @Override
    public void setPresenter(@NonNull CategoriesContract.Presenter presenter) {
        this.mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mPresenter.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories_frag, container, false);

        mViewDataBinding = CategoriesFragBinding.bind(view);
        mViewDataBinding.setCategories(this.mCategoriesViewModel);
        mViewDataBinding.setActionHandler(this.mPresenter);

        CategoriesAdapter adapter = new CategoriesAdapter(mPresenter);
        mCategoriesViewModel.setAdapter(adapter);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout = mViewDataBinding.refreshLayout;
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(mViewDataBinding.rvCategories);

        return mViewDataBinding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                Integer parentId = mCategoriesViewModel.getParent() != null ? mCategoriesViewModel.getParent().getId() : null;
                // todo: refresh current categories, do not reset to null
                mPresenter.loadCategories(null);
//                Integer categoryId = mAchievementsViewModel.getCategory() != null ? mAchievementsViewModel.getCategory().getId() : null;
//                mPresenter.loadAchievements(categoryId, true);
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.categories_fragment_menu, menu);
    }

    public void setViewModel(CategoriesViewModel categoriesViewModel) {
        this.mCategoriesViewModel = categoriesViewModel;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) return;

        final SwipeRefreshLayout srl = (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showCategories(Cursor cursor) {
        mCategoriesViewModel.setCursor(cursor);

        int cursorSize = CursorUtils.getSize(cursor);
        mCategoriesViewModel.setCategoriesListSize(cursorSize);
    }

    @Override
    public void showNoCategories() {
        mCategoriesViewModel.setCategoriesListSize(0);
        mCategoriesViewModel.setLoading(false);
    }

    @Override
    public void showParent(Category parent) {
        mCategoriesViewModel.setParent(parent);
    }

    @Override
    public void showLoadingCategoriesError(String message) {
        if (message == null || message.length() == 0) {
            message = getString(R.string.loading_categories_error);
        }

        showMessage(message);
    }

    @Override
    public void showLoadingParentError() {
        showMessage(getString(R.string.loading_parent_category_error));
    }

    private void showMessage(String message) {
        if (getView() == null) return;

        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}