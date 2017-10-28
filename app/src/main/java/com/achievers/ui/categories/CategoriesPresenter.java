package com.achievers.ui.categories;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.achievers.data.entities.Category;
import com.achievers.data.source.categories.CategoriesDataSource;
import com.achievers.data.source.categories.CategoriesRepository;
import com.achievers.ui._base.AbstractPresenter;

import java.util.EmptyStackException;
import java.util.Stack;

import static com.achievers.utils.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link CategoriesFragment}), retrieves the data and updates the
 * UI as required.
 */
public class CategoriesPresenter
        extends AbstractPresenter<CategoriesContract.View>
        implements CategoriesContract.Presenter,
//        LoaderManager.LoaderCallbacks<Cursor>,
        CategoriesRepository.LoadDataCallback
//        CategoriesContract.AchievementsPresenter.OpenAchievementCallback {
{

//    private static final int CATEGORIES_LOADER_ID = 1;
    private static final String PARENT_ID_KEY = "parent_id";
    private static final int ROOT_CATEGORY_ID = -1; // it is -1 because stack can't handle nulls

//    private final Context mContext;
//    private final LoaderManager mLoaderManager;
//    private final CategoriesLoaderProvider mLoaderProvider;

    private final CategoriesDataSource mCategoriesDataSource;
    private Stack<Long> mCategoriesNavigationState;

    public CategoriesPresenter(
//            @NonNull Context context,
//            @NonNull LoaderManager loaderManager,
//            @NonNull CategoriesLoaderProvider loaderProvider,
            @NonNull CategoriesContract.View categoriesView,
            @NonNull CategoriesDataSource categoriesDataSource) {

//        this.mContext = context;
//        this.mLoaderManager = checkNotNull(loaderManager, "loaderManager cannot be null!");
//        this.mLoaderProvider = checkNotNull(loaderProvider, "loaderProvider cannot be null!");
        mView = checkNotNull(categoriesView, "view cannot be null!");
        mCategoriesDataSource = checkNotNull(categoriesDataSource, "categoriesDataSource cannot be null");

        mCategoriesNavigationState = new Stack<>();
//        this.mOpenAchievementCallback = new OpenAchievementCallback() {
//            @Override
//            public void onOpen(Integer categoryId) {
//                Intent intent = new Intent(mContext, AchievementsActivity.class);
//                intent.putExtra(AchievementsActivity.EXTRA_CATEGORY_ID, categoryId);
//                mContext.startActivity(intent);
//            }
//        };
    }

//    @Override
//    public void onOpen(Integer categoryId) {
//        // load new category
//    }

    @Override
    public void start() {
        loadCategories(null);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        // If a Category was successfully added, show snackbar
//        if (AddEditCategoryActivity.REQUEST_ADD_CATEGORY == requestCode && AchievementsActivity.RESULT_OK == resultCode) {
//            mCategoriesView.showSuccessfullySavedMessage();
//        }
    }

    @Override
    public void loadCategories(final Long parentId) {
        mView.setLoadingIndicator(true);

        mCategoriesDataSource.load(parentId);

//        mCategoriesDataSource.load(parentId, new LoadCallback<Category>() {
//            @Override
//            public void onSuccess(List<Category> data) {
//                // todo
////                Bundle args = new Bundle();
////                args.putString(PARENT_ID_KEY, String.valueOf(parentId));
////
////                // we don't care about the resultPermission since the CursorLoader will load the data for us
////                if (mLoaderManager.getLoader(CATEGORIES_LOADER_ID) == null) {
////                    mLoaderManager.initLoader(CATEGORIES_LOADER_ID, args, CategoriesPresenter.this);
////                    return;
////                }
////
////                mLoaderManager.restartLoader(CATEGORIES_LOADER_ID, args, CategoriesPresenter.this);
//            }
//
//            @Override
//            public void onFailure(String message) {
//                mCategoriesView.setLoadingIndicator(false);
//                mCategoriesView.showLoadingCategoriesError(message);
//            }
//        });
    }

    @Override
    public void openCategory(@NonNull Category requestedCategory) {
        checkNotNull(requestedCategory, "requestedCategory cannot be null!");

        this.loadCategories(requestedCategory.getId());

        Long parentId = requestedCategory.getParentId();
        Long navigationId = parentId != null ? parentId : ROOT_CATEGORY_ID;
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
            Long categoryId = this.mCategoriesNavigationState.pop();
            if (categoryId == ROOT_CATEGORY_ID) categoryId = null;

            this.loadCategories(categoryId);

            return true;
        } catch (EmptyStackException exc) { // stack is empty so there is no previous category
            return false;
        }
    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        String parentId = args.getString(PARENT_ID_KEY, null);
//        return mLoaderProvider.createCategoriesLoader(parentId);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        if (data != null) {
//            if (data.moveToLast()) {
//                onDataLoaded(data);
//            } else {
//                onDataEmpty();
//            }
//        } else {
//            onDataNotAvailable();
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//        onDataReset();
//    }

    @Override
    public void onDataLoaded(Cursor data) {
        mView.setLoadingIndicator(false);
        mView.showCategories(data);
    }

    @Override
    public void onDataEmpty() {
        mView.setLoadingIndicator(false);
        mView.showNoCategories();
    }

    @Override
    public void onDataNotAvailable() {
        mView.setLoadingIndicator(false);
        mView.showLoadingCategoriesError(null);
    }

    @Override
    public void onDataReset() {
        mView.showCategories(null);
    }
}