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
    // todo: implement cache as hashtable/dictionary with key categoryId and boolean value if it should be cached
    private boolean mCacheIsDirty;

    // Prevent direct instantiation
    private CategoriesRepository(@NonNull CategoriesDataSource categoriesRemoteDataSource, @NonNull CategoriesDataSource categoriesLocalDataSource) {
        this.mCategoriesRemoteDataSource = checkNotNull(categoriesRemoteDataSource);
        this.mCategoriesLocalDataSource = checkNotNull(categoriesLocalDataSource);
        this.refreshCache();
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
    public void loadCategories(final Integer parentId, @NonNull final LoadCategoriesCallback callback) {
        checkNotNull(callback);

        if (this.mCacheIsDirty) { // the cache is dirty so we need to load new data from the remote data source
            this.mCategoriesRemoteDataSource.loadCategories(parentId, new LoadCategoriesCallback() {
                @Override
                public void onLoaded(List<Category> categories) {
                    callback.onLoaded(categories);

                    saveCategories(categories, new SaveCategoriesCallback() {
                        @Override
                        public void onSuccess() {
                            mCacheIsDirty = false; // cache is clean so the next call will return results form local data source
                        }

                        @Override
                        public void onError() {
                            refreshCache();
                        }
                    });
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });

            return; // we are done
        }

        // return result by querying the local storage (realm)
        mCategoriesLocalDataSource.loadCategories(parentId, new LoadCategoriesCallback() {
            @Override
            public void onLoaded(List<Category> categories) {
                callback.onLoaded(categories);
            }

            @Override
            public void onDataNotAvailable() {
                // table is new or empty so load data from remote data source
                refreshCache();
//                callback.onDataNotAvailable();
                loadCategories(parentId, callback);
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
    public void getCategory(@NonNull final Integer categoryId, @NonNull final GetCategoryCallback callback) {
        checkNotNull(callback);

        // todo: implement cache strategy
        // Load from server/persisted
        // Is category in the local data source? If not, query the network.
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
     * Saves Categories list only to local data source.
     */
    @Override
    public void saveCategories(@NonNull List<Category> categories, @NonNull SaveCategoriesCallback callback) {
        this.mCategoriesLocalDataSource.saveCategories(categories, callback);
    }

    @Override
    public void refreshCache() {
        this.mCacheIsDirty = true;
    }
}