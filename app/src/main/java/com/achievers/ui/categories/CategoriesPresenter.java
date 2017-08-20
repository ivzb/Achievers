package com.achievers.ui.categories;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;

import com.achievers.provider.AchieversContract;
import com.achievers.provider.AchieversDatabase;
import com.achievers.sync.SyncUtils;
import com.achievers.ui.achievements.AchievementsActivity;
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
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_CATEGORIES_LOADER = 44;

    private final Context mContext;
    private final LoaderManager mLoaderManager;
    private final CategoriesDataSource mCategoriesDataSource;
    private final CategoriesContract.View mCategoriesView;
    private CategoriesFilterType mCurrentFiltering = CategoriesFilterType.ALL_CATEGORIES;
    private boolean mFirstLoad;
    private Stack<Integer> mCategoriesNavigationState;
    private OpenAchievementCallback mOpenAchievementCallback;

    public CategoriesPresenter(
            @NonNull Context context,
            @NonNull LoaderManager loaderManager,
            @NonNull CategoriesDataSource categoriesDataSource,
            @NonNull CategoriesContract.View categoriesView) {

        this.mContext = context;
        this.mLoaderManager = loaderManager;
        this.mCategoriesDataSource = checkNotNull(categoriesDataSource, "categoriesDataSource cannot be null");
        this.mCategoriesView = checkNotNull(categoriesView, "categoriesView cannot be null!");
        this.mCategoriesView.setPresenter(this);
        this.mFirstLoad = true;
        this.mCategoriesNavigationState = new Stack<>();
        this.mOpenAchievementCallback = new OpenAchievementCallback() {
            @Override
            public void onOpen(Integer categoryId) {
                Intent intent = new Intent(mContext, AchievementsActivity.class);
                intent.putExtra(AchievementsActivity.EXTRA_CATEGORY_ID, categoryId);
                mContext.startActivity(intent);
            }
        };
    }

    @Override
    public void start() {
        mLoaderManager.initLoader(ID_CATEGORIES_LOADER, null, this);

        SyncUtils.startSync(mContext, AchieversContract.Categories.buildCategoriesUri());
    }

    @Override
    public void result(int requestCode, int resultCode) {
        // If a Category was successfully added, show snackbar
//        if (AddEditCategoryActivity.REQUEST_ADD_CATEGORY == requestCode && Activity.RESULT_OK == resultCode) {
//            mCategoriesView.showSuccessfullySavedMessage();
//        }
    }

    @Override
    public void loadCategories(Integer parentId, boolean forceUpdate) {
        // a network reload will be forced on first load.
        //this.loadCategories(parentId, forceUpdate || this.mFirstLoad, true, this.getOpenAchievementCallback());

        //this.mFirstLoad = false;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch (loaderId) {
            case ID_CATEGORIES_LOADER:
                Uri categoriesQueryUri = AchieversContract.Categories.buildCategoriesUri();

                String[] projection = {
                        AchieversContract.Categories.CATEGORY_ID,
                        AchieversContract.Categories.CATEGORY_TITLE,
                        AchieversContract.Categories.CATEGORY_DESCRIPTION,
                        AchieversContract.Categories.CATEGORY_IMAGE_URL,
                        AchieversContract.Categories.CATEGORY_PARENT_ID
                };

                String sortOrder = AchieversContract.Categories.CATEGORY_ID + " ASC";

                return new CursorLoader(mContext,
                        categoriesQueryUri,
                        projection,
                        null, null,
                        sortOrder);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        mForecastAdapter.swapCursor(data);
//        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
//        mRecyclerView.smoothScrollToPosition(mPosition);
//        if (data.getCount() != 0) showWeatherDataView();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
//        mForecastAdapter.swapCursor(null);
        double a = Math.sqrt(5);
    }

    //    /**
//     * @param forceUpdate   Pass in true to refresh the data in the {@link CategoriesDataSource}
//     * @param showLoadingUI Pass in true to display a loading icon in the UI
//     */
//    private void loadCategories(
//            final Integer parentCategoryId,
//            boolean forceUpdate,
//            final boolean showLoadingUI,
//            final OpenAchievementCallback callback) {
//
//        if (showLoadingUI) mCategoriesView.setLoadingIndicator(true);
//
//        mCategoriesDataSource.loadCategories(new LoadCallback<Category>() {
//            @Override
//            public void onSuccess(List<Category> categories) {
//                // TODO: Fix filtering
////                List<Category> categoriesToShow = new ArrayList<>();
////
//                // filter the categories based on the requestType
////                for (Category category: categories) {
////                    switch (mCurrentFiltering) {
////                        case ALL_CATEGORIES:
////                            categories.add(category);
////                            break;
////                    }
////                }
//
//                // The view may not be able to handle UI updates anymore
//                if (!mCategoriesView.isActive()) return;
//                if (showLoadingUI) mCategoriesView.setLoadingIndicator(false);
//
//                mCategoriesView.showCategories(categories);
//            }
//
//            @Override
//            public void onFailure(String message) {
//                // The view may not be able to handle UI updates anymore
//                if (!mCategoriesView.isActive()) return;
//                mCategoriesView.showLoadingCategoriesError();
//                if (showLoadingUI) mCategoriesView.setLoadingIndicator(false);
//                if (mCategoriesNavigationState.size() > 0) mCategoriesNavigationState.pop();
//
//                // TODO: show error message
//            }
//        });
//    }

    @Override
    public void openCategoryDetails(@NonNull Category requestedCategory, OpenAchievementCallback callback) {
        checkNotNull(requestedCategory, "requestedCategory cannot be null!");
        checkNotNull(callback, "callback cannot be null!");

//        this.loadCategories(requestedCategory.getId(), true, true, callback);

        // saving first parent as -1 because stack cant handle nulls
//        mCategoriesNavigationState.add(requestedCategory.getParent() == null || requestedCategory.getParent().getId() == null ? -1 : requestedCategory.getParent().getId());
    }

    /**
     * Sets the current category filtering type.
     *
     * @param requestType Can be {@link CategoriesFilterType#ALL_CATEGORIES}
     */
    @Override
    public void setFiltering(CategoriesFilterType requestType) {
        mCurrentFiltering = requestType;
    }

    @Override
    public CategoriesFilterType getFiltering() {
        return mCurrentFiltering;
    }

    @Override
    public OpenAchievementCallback getOpenAchievementCallback() {
        return this.mOpenAchievementCallback;
    }

    /**
     * Checks if there are any Categories in the stack which can be
     * navigated back and if there are any, pops last one, refreshes adapter and returns true.
     * Otherwise returns false.
     */
    @Override
    public boolean navigateToPreviousCategory() {
        try {
            Integer categoryId = this.mCategoriesNavigationState.pop();
            this.loadCategories(categoryId == -1 ? null : categoryId, true);

            return true;
        } catch (EmptyStackException exc) { // stack is empty so there is no previous category
            return false;
        }
    }
}