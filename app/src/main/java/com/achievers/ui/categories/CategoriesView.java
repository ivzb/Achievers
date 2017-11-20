package com.achievers.ui.categories;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Category;
import com.achievers.databinding.CategoriesFragBinding;
import com.achievers.provider.AppContentProvider;
import com.achievers.ui._base.AbstractView;
import com.achievers.utils.CursorUtils;
import com.achievers.utils.ui.ScrollChildSwipeRefreshLayout;
import com.achievers.utils.ui.SwipeRefreshLayoutUtils;

public class CategoriesView
        extends AbstractView<CategoriesContract.Presenter, CategoriesContract.ViewModel, CategoriesFragBinding>
        implements CategoriesContract.View<CategoriesFragBinding> {

    private static final int LOADER_CATEGORIES = 1;

    public CategoriesView() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories_frag, container, false);

        mDataBinding = CategoriesFragBinding.bind(view);
        mDataBinding.setViewModel(mViewModel);
        mDataBinding.setActionHandler(mPresenter);

        CategoriesAdapter adapter = new CategoriesAdapter(mPresenter);
        mViewModel.setAdapter(adapter);

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout = mDataBinding.refreshLayout;
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.primary),
                ContextCompat.getColor(getActivity(), R.color.accent),
                ContextCompat.getColor(getActivity(), R.color.dark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(mDataBinding.rvCategories);

        getActivity().getSupportLoaderManager().initLoader(LOADER_CATEGORIES, null, mLoaderCallbacks);

        return mDataBinding.getRoot();
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {

                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case LOADER_CATEGORIES:
                            return new CursorLoader(getActivity().getApplicationContext(),
                                    AppContentProvider.URI_CATEGORY,
                                    new String[]{Category.COLUMN_TITLE},
                                    null, null, null);
                        default:
                            throw new IllegalArgumentException();
                    }
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    switch (loader.getId()) {
                        case LOADER_CATEGORIES:
                            mViewModel.setCursor(data);
                            break;
                    }
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    switch (loader.getId()) {
                        case LOADER_CATEGORIES:
                            mViewModel.setCursor(null);
                            break;
                    }
                }

            };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                Long parentId = mViewModel.getParent() != null ? mViewModel.getParent().getId() : null;
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

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) return;

        SwipeRefreshLayoutUtils.setLoading(mDataBinding.refreshLayout, active);
    }

    @Override
    public void showCategories(Cursor cursor) {
        mViewModel.setCursor(cursor);

        int cursorSize = CursorUtils.getSize(cursor);
        mViewModel.setCategoriesListSize(cursorSize);
    }

    @Override
    public void showNoCategories() {
        mViewModel.setCategoriesListSize(0);
        mViewModel.setLoading(false);
    }

    @Override
    public void showParent(Category parent) {
        mViewModel.setParent(parent);
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