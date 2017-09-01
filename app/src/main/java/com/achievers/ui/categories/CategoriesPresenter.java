package com.achievers.ui.categories;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.achievers.data.source.categories.CategoriesLoaderProvider;
import com.achievers.data.source.categories.CategoriesRepository;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.entities.Category;
import com.achievers.data.source.categories.CategoriesDataSource;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link CategoriesFragment}), retrieves the data and updates the
 * UI as required.
 */
public class CategoriesPresenter implements CategoriesContract.Presenter,
        LoaderManager.LoaderCallbacks<Cursor>,
        CategoriesRepository.LoadDataCallback,
        CategoriesContract.Presenter.OpenAchievementCallback {

    private static final int CATEGORIES_LOADER_ID = 1;
    private static final String PARENT_ID_KEY = "parent_id";
    private static final int ROOT_CATEGORY_ID = -1; // it is -1 because stack can't handle nulls

//    private final Context mContext;
private final LoaderManager mLoaderManager;
    private final CategoriesLoaderProvider mLoaderProvider;
    private final CategoriesDataSource mCategoriesDataSource;
    private final CategoriesContract.View mCategoriesView;
    private Stack<Integer> mCategoriesNavigationState;

    public CategoriesPresenter(
//            @NonNull Context context,
            @NonNull LoaderManager loaderManager,
            @NonNull CategoriesLoaderProvider loaderProvider,
            @NonNull CategoriesDataSource categoriesDataSource,
            @NonNull CategoriesContract.View categoriesView) {

//        this.mContext = context;
        this.mLoaderManager = checkNotNull(loaderManager, "loaderManager cannot be null!");
        this.mLoaderProvider = checkNotNull(loaderProvider, "loaderProvider cannot be null!");
        this.mCategoriesDataSource = checkNotNull(categoriesDataSource, "categoriesDataSource cannot be null");
        this.mCategoriesView = checkNotNull(categoriesView, "categoriesView cannot be null!");

        this.mCategoriesNavigationState = new Stack<>();
//        this.mOpenAchievementCallback = new OpenAchievementCallback() {
//            @Override
//            public void onOpen(Integer categoryId) {
//                Intent intent = new Intent(mContext, AchievementsActivity.class);
//                intent.putExtra(AchievementsActivity.EXTRA_CATEGORY_ID, categoryId);
//                mContext.startActivity(intent);
//            }
//        };
    }

    @Override
    public void onOpen(Integer categoryId) {
        // load new category
    }

    @Override
    public void start() {
        loadCategories(null);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        // If a Category was successfully added, show snackbar
//        if (AddEditCategoryActivity.REQUEST_ADD_CATEGORY == requestCode && Activity.RESULT_OK == resultCode) {
//            mCategoriesView.showSuccessfullySavedMessage();
//        }
    }

    @Override
    public void loadCategories(final Integer parentId) {
        mCategoriesView.setLoadingIndicator(true);

        mCategoriesDataSource.load(parentId, new LoadCallback<Category>() {
            @Override
            public void onSuccess(List<Category> data) {
                Bundle args = new Bundle();
                args.putString(PARENT_ID_KEY, String.valueOf(parentId));

                // we don't care about the result since the CursorLoader will load the data for us
                if (mLoaderManager.getLoader(CATEGORIES_LOADER_ID) == null) {
                    mLoaderManager.initLoader(CATEGORIES_LOADER_ID, args, CategoriesPresenter.this);
                    return;
                }

                mLoaderManager.restartLoader(CATEGORIES_LOADER_ID, args, CategoriesPresenter.this);
            }

            @Override
            public void onFailure(String message) {
                mCategoriesView.setLoadingIndicator(false);
                mCategoriesView.showLoadingCategoriesError(message);
            }
        });
    }

    @Override
    public void openCategory(@NonNull Category requestedCategory) {
        checkNotNull(requestedCategory, "requestedCategory cannot be null!");

        this.loadCategories(requestedCategory.getId());

        Integer parentId = requestedCategory.getParentId();
        Integer navigationId = parentId != null ? parentId : ROOT_CATEGORY_ID;
        mCategoriesNavigationState.add(navigationId);
    }

//    @Override
//    public OpenAchievementCallback getOpenAchievementCallback() {
//        return this.mOpenAchievementCallback;
//    }
//
    /**
     * Checks if there are any Categories in the stack which can be
     * navigated back and if there are any, pops last one, refreshes adapter and returns true.
     * Otherwise returns false.
     */
    @Override
    public boolean navigateToPreviousCategory() {
        try {
            Integer categoryId = this.mCategoriesNavigationState.pop();
            if (categoryId == ROOT_CATEGORY_ID) categoryId = null;

            this.loadCategories(categoryId);

            return true;
        } catch (EmptyStackException exc) { // stack is empty so there is no previous category
            return false;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String parentId = args.getString(PARENT_ID_KEY, null);
        return mLoaderProvider.createCategoriesLoader(parentId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            if (data.moveToLast()) {
                onDataLoaded(data);
            } else {
                onDataEmpty();
            }
        } else {
            onDataNotAvailable();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        onDataReset();
    }

    @Override
    public void onDataLoaded(Cursor data) {
        mCategoriesView.setLoadingIndicator(false);
        mCategoriesView.showCategories(data);
    }

    @Override
    public void onDataEmpty() {
        mCategoriesView.setLoadingIndicator(false);
        mCategoriesView.showNoCategories();
    }

    @Override
    public void onDataNotAvailable() {
        mCategoriesView.setLoadingIndicator(false);
        mCategoriesView.showLoadingCategoriesError(null);
    }

    @Override
    public void onDataReset() {
        mCategoriesView.showCategories(null);
    }
}