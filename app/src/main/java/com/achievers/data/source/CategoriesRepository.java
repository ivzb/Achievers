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
    public void getCategories(@NonNull final LoadCategoriesCallback callback) {
        checkNotNull(callback);

        if (this.mCacheIsDirty) { // If the cache is dirty we need to fetch new data from the network.
            mCategoriesRemoteDataSource.getCategories(new LoadCategoriesCallback() {
                @Override
                public void onLoaded(List<Category> categories) {
                    mCategoriesLocalDataSource.refreshCategories(categories);
                    callback.onLoaded(categories);
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });
            return;
        }

        // Query the local storage if available. If not, query the network.
        mCategoriesLocalDataSource.getCategories(new LoadCategoriesCallback() {
            @Override
            public void onLoaded(List<Category> categories) {
                callback.onLoaded(categories);
            }

            @Override
            public void onDataNotAvailable() {
                mCacheIsDirty = true;
                getCategories(callback);
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

    @Override
    public void refreshCategories(List<Category> categories) {
        this.mCacheIsDirty = false;
        this.mCategoriesLocalDataSource.refreshCategories(categories);
    }
}