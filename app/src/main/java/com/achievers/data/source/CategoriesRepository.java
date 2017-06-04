package com.achievers.data.source;

import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;

import com.achievers.data.Category;
import com.achievers.data.source.callbacks.GetCallback;
import com.achievers.data.source.callbacks.LoadCallback;
import com.achievers.data.source.callbacks.SaveCallback;

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
    private SparseBooleanArray mAlreadyBeenHere;

    // Prevent direct instantiation
    private CategoriesRepository(
            @NonNull CategoriesDataSource categoriesRemoteDataSource,
            @NonNull CategoriesDataSource categoriesLocalDataSource
    ) {
        this.mCategoriesRemoteDataSource = checkNotNull(categoriesRemoteDataSource);
        this.mCategoriesLocalDataSource = checkNotNull(categoriesLocalDataSource);
        this.refreshCache();
        this.mAlreadyBeenHere = new SparseBooleanArray();
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param categoriesRemoteDataSource the backend data source
     * @param categoriesLocalDataSource  the device storage data source
     * @return the {@link CategoriesRepository} instance
     */
    public static CategoriesRepository getInstance(
            CategoriesDataSource categoriesRemoteDataSource,
            CategoriesDataSource categoriesLocalDataSource
    ) {
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
     * Note: {@link LoadCallback<List<Category>>#onFailure()} is fired if all data sources fail to
     * get the data.
     * </p>
     */
    @Override
    public void loadCategories(
            final Integer parentId,
            @NonNull final LoadCallback<List<Category>> callback
    ) {
        checkNotNull(callback);

        if (this.mCacheIsDirty) { // the cache is dirty so we need to load new data from the remote data source
            this.mCategoriesRemoteDataSource.loadCategories(parentId, new LoadCallback<List<Category>>() {
                @Override
                public void onSuccess(List<Category> categories) {
                    mAlreadyBeenHere.put(parentId != null ? parentId : -1, false);
                    callback.onSuccess(categories);

                    saveCategories(categories, new SaveCallback<Void>() {
                        @Override
                        public void onSuccess(Void data) {
                            mCacheIsDirty = false; // cache is clean so the next call will return results form local data source
                        }

                        @Override
                        public void onFailure(String message) {
                            refreshCache();
                        }
                    });
                }

                @Override
                public void onNoMoreData() {
                    mAlreadyBeenHere.put(parentId != null ? parentId : -1, false);
                    callback.onNoMoreData();
                }

                @Override
                public void onFailure(String message) {
                    if (mAlreadyBeenHere.get(parentId != null ? parentId : -1, false)) {
                        mAlreadyBeenHere.put(parentId != null ? parentId : -1, false);
                        callback.onFailure(message);
                        return;
                    }

                    mCacheIsDirty = false;
                    mAlreadyBeenHere.put(parentId != null ? parentId : -1, true);
                    loadCategories(parentId, callback);
                }
            });

            return; // we are done
        }

        // return result by querying the local storage (realm)
        mCategoriesLocalDataSource.loadCategories(parentId, new LoadCallback<List<Category>>() {
            @Override
            public void onSuccess(List<Category> categories) {
                mAlreadyBeenHere.put(parentId != null ? parentId : -1, false);
                callback.onSuccess(categories);
            }

            @Override
            public void onNoMoreData() {
                mAlreadyBeenHere.put(parentId != null ? parentId : -1, false);
                callback.onNoMoreData();
            }

            @Override
            public void onFailure(String message) {
                // table is new or empty so load data from remote data source
                refreshCache();
                mAlreadyBeenHere.put(parentId != null ? parentId : -1, true);
                loadCategories(parentId, callback);
            }
        });
    }

    @Override
    public void getCategory(
            @NonNull final Integer categoryId,
            @NonNull final GetCallback<Category> callback
    ) {
        checkNotNull(callback);

        // todo: implement cache strategy
        // Load from server/persisted
        // Is category in the local data source? If not, query the network.
        mCategoriesLocalDataSource.getCategory(categoryId, new GetCallback<Category>() {
            @Override
            public void onSuccess(Category category) {
                callback.onSuccess(category);
            }

            @Override
            public void onFailure(String message) {
                mCategoriesRemoteDataSource.getCategory(categoryId, new GetCallback<Category>() {
                    @Override
                    public void onSuccess(Category category) {
                        callback.onSuccess(category);
                    }

                    @Override
                    public void onFailure(String message) {
                        callback.onFailure(message);
                    }
                });
            }
        });
    }

    /**
     * Saves Categories list only to local data source.
     */
    @Override
    public void saveCategories(@NonNull List<Category> categories, @NonNull SaveCallback<Void> callback) {
        this.mCategoriesLocalDataSource.saveCategories(categories, callback);
    }

    @Override
    public void refreshCache() {
        this.mCacheIsDirty = true;
    }
}