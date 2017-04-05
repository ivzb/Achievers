package com.achievers.data.source;

import android.support.annotation.NonNull;

import com.achievers.data.Category;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load Categories from the data sources.
 * <p>
 * This implements a synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source on every application start.
 * </p>
 */
public class CategoriesRepository implements CategoriesDataSource {

    private static CategoriesRepository INSTANCE = null;
    private final CategoriesDataSource mCategoriesRemoteDataSource;
    private final CategoriesDataSource mCategoriesLocalDataSource;
    private boolean mCacheIsDirty = true;

    // Prevent direct instantiation.
    private CategoriesRepository(@NonNull CategoriesDataSource categoriesRemoteDataSource, @NonNull CategoriesDataSource categoriesLocalDataSource) {
        this.mCategoriesRemoteDataSource = checkNotNull(categoriesRemoteDataSource);
        this.mCategoriesLocalDataSource = checkNotNull(categoriesLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param categoriesRemoteDataSource the backend data source
     * @param categoriesLocalDataSource  the device storage data source
     * @return the {@link CategoriesRepository} instance
     */
    public static CategoriesRepository getInstance(CategoriesDataSource categoriesRemoteDataSource, CategoriesDataSource categoriesLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CategoriesRepository(categoriesRemoteDataSource, categoriesLocalDataSource);
        }

        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(CategoriesDataSource, CategoriesDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets Categories from local data source (realm) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadCategoriesCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     * </p>
     */
    @Override
    public void getCategories(final Integer parentId, @NonNull final LoadCategoriesCallback callback) {
        checkNotNull(callback);

        if (this.mCacheIsDirty) { // the cache is dirty so we need to fetch new data from the network
            this.mCategoriesRemoteDataSource.getCategories(parentId, new LoadCategoriesCallback() {
                @Override
                public void onLoaded(List<Category> categories) {
                    mCacheIsDirty = false; // cache is clean so the next call will return results form local data source
                    saveCategories(categories); // saving results to local data source
                    getCategories(parentId, callback); // recursively call SELF in order to return data from local data source
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });

            return; // stop execution until saved all categories
        }

        // return result by querying the local storage
        mCategoriesLocalDataSource.getCategories(parentId, new LoadCategoriesCallback() {
            @Override
            public void onLoaded(List<Category> categories) {
                callback.onLoaded(categories);
            }

            @Override
            public void onDataNotAvailable() {
                mCacheIsDirty = true; // if no data available make cache dirty in order to fetch data from the network next time
                callback.onDataNotAvailable();
            }
        });
    }

    /**
     * Gets Categories from local data source (realm) unless the table is new or empty. In that case it
     * uses the network data source.
     * <p>
     * Note: {@link GetCategoryCallback#onDataNotAvailable()} is fired if both data sources fail to
     * get the data.
     */
    @Override
    public void getCategory(@NonNull final int categoryId, @NonNull final GetCategoryCallback callback) {
        checkNotNull(categoryId);
        checkNotNull(callback);

        // Load from server/persisted
        // Is the task in the local data source? If not, query the network.
        mCategoriesLocalDataSource.getCategory(categoryId, new GetCategoryCallback() {
            @Override
            public void onLoaded(Category category) {
                callback.onLoaded(category);
            }

            @Override
            public void onDataNotAvailable() {
                mCategoriesRemoteDataSource.getCategory(categoryId, new GetCategoryCallback() {
                    @Override
                    public void onLoaded(Category category) {
                        callback.onLoaded(category);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    /**
     * Saves Category object to local data source.
     */
    public void saveCategory(@NonNull Category category) {
        this.mCategoriesLocalDataSource.saveCategory(category);
    }

    @Override
    public void refreshCache() {
        this.mCacheIsDirty = true;
    }

    private void saveCategories(List<Category> categories) {
        if (categories.size() == 0) return;

        int lastElementIndex = categories.size() - 1;
        Category categoryToBeSaved = categories.remove(lastElementIndex);
        this.saveCategory(categoryToBeSaved);

        this.saveCategories(categories);
    }
}