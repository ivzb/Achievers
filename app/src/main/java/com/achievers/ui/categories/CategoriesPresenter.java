package com.achievers.ui.categories;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.achievers.ui.achievements.AchievementsActivity;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.models.Category;
import com.achievers.data.source.categories.CategoriesDataSource;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link CategoriesFragment}), retrieves the data and updates the
 * UI as required.
 */
public class CategoriesPresenter implements CategoriesContract.Presenter {

    private final Context mContext;
    private final CategoriesDataSource mCategoriesDataSource;
    private final CategoriesContract.View mCategoriesView;
    private CategoriesFilterType mCurrentFiltering = CategoriesFilterType.ALL_CATEGORIES;
    private boolean mFirstLoad;
    private Stack<Integer> mCategoriesNavigationState;
    private OpenAchievementCallback mOpenAchievementCallback;

    public CategoriesPresenter(
            @NonNull Context context,
            @NonNull CategoriesDataSource categoriesDataSource,
            @NonNull CategoriesContract.View categoriesView) {

        this.mContext = context;
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
    public void result(int requestCode, int resultCode) {
        // If a Category was successfully added, show snackbar
//        if (AddEditCategoryActivity.REQUEST_ADD_CATEGORY == requestCode && Activity.RESULT_OK == resultCode) {
//            mCategoriesView.showSuccessfullySavedMessage();
//        }
    }

    @Override
    public void loadCategories(Integer parentId, boolean forceUpdate) {
        // a network reload will be forced on first load.
        this.loadCategories(parentId, forceUpdate || this.mFirstLoad, true, this.getOpenAchievementCallback());

        this.mFirstLoad = false;
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link CategoriesDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadCategories(
            final Integer parentCategoryId,
            boolean forceUpdate,
            final boolean showLoadingUI,
            final OpenAchievementCallback callback) {

        if (showLoadingUI) mCategoriesView.setLoadingIndicator(true);

        mCategoriesDataSource.loadCategories(new LoadCallback<Category>() {
            @Override
            public void onSuccess(List<Category> categories) {
                // TODO: Fix filtering
//                List<Category> categoriesToShow = new ArrayList<>();
//
                // filter the categories based on the requestType
//                for (Category category: categories) {
//                    switch (mCurrentFiltering) {
//                        case ALL_CATEGORIES:
//                            categories.add(category);
//                            break;
//                    }
//                }

                // The view may not be able to handle UI updates anymore
                if (!mCategoriesView.isActive()) return;
                if (showLoadingUI) mCategoriesView.setLoadingIndicator(false);

                mCategoriesView.showCategories(categories);
            }

            @Override
            public void onFailure(String message) {
                // The view may not be able to handle UI updates anymore
                if (!mCategoriesView.isActive()) return;
                mCategoriesView.showLoadingCategoriesError();
                if (showLoadingUI) mCategoriesView.setLoadingIndicator(false);
                if (mCategoriesNavigationState.size() > 0) mCategoriesNavigationState.pop();

                // TODO: show error message
            }
        });
    }

    @Override
    public void openCategoryDetails(@NonNull Category requestedCategory, OpenAchievementCallback callback) {
        checkNotNull(requestedCategory, "requestedCategory cannot be null!");
        checkNotNull(callback, "callback cannot be null!");

        this.loadCategories(requestedCategory.getId(), true, true, callback);

        // saving first parent as -1 because stack cant handle nulls
        mCategoriesNavigationState.add(requestedCategory.getParent() == null || requestedCategory.getParent().getId() == null ? -1 : requestedCategory.getParent().getId());
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