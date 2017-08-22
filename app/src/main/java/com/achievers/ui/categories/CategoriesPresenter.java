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

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link CategoriesFragment}), retrieves the data and updates the
 * UI as required.
 */
public class CategoriesPresenter implements CategoriesContract.Presenter,
        LoaderManager.LoaderCallbacks<Cursor>,
        LoadCallback<Category>,
        CategoriesRepository.LoadDataCallback {

    private static final int CATEGORIES_LOADER_ID = 1;

    private final Context mContext;
    private final CategoriesLoaderProvider mLoaderProvider;
    private final LoaderManager mLoaderManager;
    private final CategoriesDataSource mCategoriesDataSource;
    private final CategoriesContract.View mCategoriesView;
//    private boolean mFirstLoad;
//    private Stack<Integer> mCategoriesNavigationState;
//    private OpenAchievementCallback mOpenAchievementCallback;

    public CategoriesPresenter(
            @NonNull Context context,
            @NonNull LoaderManager loaderManager,
            @NonNull CategoriesLoaderProvider loaderProvider,
            @NonNull CategoriesDataSource categoriesDataSource,
            @NonNull CategoriesContract.View categoriesView) {

        this.mContext = context;
        this.mLoaderManager = loaderManager;
        this.mLoaderProvider = checkNotNull(loaderProvider, "loaderProvider cannot be null!");
        this.mCategoriesDataSource = checkNotNull(categoriesDataSource, "categoriesDataSource cannot be null");
        this.mCategoriesView = checkNotNull(categoriesView, "categoriesView cannot be null!");

//        this.mCategoriesNavigationState = new Stack<>();
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
    public void start() {
        loadCategories();
    }

    @Override
    public void result(int requestCode, int resultCode) {
        // If a Category was successfully added, show snackbar
//        if (AddEditCategoryActivity.REQUEST_ADD_CATEGORY == requestCode && Activity.RESULT_OK == resultCode) {
//            mCategoriesView.showSuccessfullySavedMessage();
//        }
    }

    @Override
    public void loadCategories(/*Integer parentId, boolean forceUpdate*/) {
        // a network reload will be forced on first load.
        //this.loadCategories(parentId, forceUpdate || this.mFirstLoad, true, this.getOpenAchievementCallback());

        //this.mFirstLoad = false;

        mCategoriesView.setLoadingIndicator(true);

        mCategoriesDataSource.load(this);
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

//    @Override
//    public void openCategoryDetails(@NonNull Category requestedCategory, OpenAchievementCallback callback) {
//        checkNotNull(requestedCategory, "requestedCategory cannot be null!");
//        checkNotNull(callback, "callback cannot be null!");
//
////        this.loadCategories(requestedCategory.getId(), true, true, callback);
//
//        // saving first parent as -1 because stack cant handle nulls
////        mCategoriesNavigationState.add(requestedCategory.getParent() == null || requestedCategory.getParent().getId() == null ? -1 : requestedCategory.getParent().getId());
//    }

//    @Override
//    public OpenAchievementCallback getOpenAchievementCallback() {
//        return this.mOpenAchievementCallback;
//    }
//
//    /**
//     * Checks if there are any Categories in the stack which can be
//     * navigated back and if there are any, pops last one, refreshes adapter and returns true.
//     * Otherwise returns false.
//     */
//    @Override
//    public boolean navigateToPreviousCategory() {
//        try {
//            Integer categoryId = this.mCategoriesNavigationState.pop();
//            this.loadCategories(categoryId == -1 ? null : categoryId, true);
//
//            return true;
//        } catch (EmptyStackException exc) { // stack is empty so there is no previous category
//            return false;
//        }
//    }

    @Override
    public void onSuccess(List<Category> data) {
        // we don't care about the result since the CursorLoader will load the data for us
        if (mLoaderManager.getLoader(CATEGORIES_LOADER_ID) == null) {
            mLoaderManager.initLoader(CATEGORIES_LOADER_ID, null, this);
            return;
        }

        mLoaderManager.restartLoader(CATEGORIES_LOADER_ID, null, this);
    }

    @Override
    public void onFailure(String message) {
        mCategoriesView.setLoadingIndicator(false);
        mCategoriesView.showLoadingCategoriesError(message);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createCategoriesLoader();
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
        // Show the list of tasks
        mCategoriesView.showCategories(data);
        // Set the filter label's text.
//        showFilterLabel();
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