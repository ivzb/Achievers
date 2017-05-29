package com.achievers.data.source;

import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;

import com.achievers.data.Achievement;
import com.achievers.data.source.callbacks.GetCallback;
import com.achievers.data.source.callbacks.LoadCallback;
import com.achievers.data.source.callbacks.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load Achievements from the data sources.
 * <p>
 * This implements a synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source on every application start.
 * </p>
 */
public class AchievementsRepository implements AchievementsDataSource {

    private static AchievementsRepository INSTANCE = null;
    private final AchievementsDataSource mAchievementsRemoteDataSource;
    private final AchievementsDataSource mAchievementsLocalDataSource;
    private boolean mCacheIsDirty;
    private SparseBooleanArray mAlreadyBeenHere;

    // Prevent direct instantiation
    private AchievementsRepository(@NonNull AchievementsDataSource achievementsRemoteDataSource, @NonNull AchievementsDataSource achievementsLocalDataSource) {
        this.mAchievementsRemoteDataSource = checkNotNull(achievementsRemoteDataSource);
        this.mAchievementsLocalDataSource = checkNotNull(achievementsLocalDataSource);
        this.refreshCache();
        this.mAlreadyBeenHere = new SparseBooleanArray();
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param achievementsRemoteDataSource the backend data source
     * @param achievementsLocalDataSource  the device storage data source
     * @return the {@link AchievementsRepository} instance
     */
    public static AchievementsRepository getInstance(AchievementsDataSource achievementsRemoteDataSource, AchievementsDataSource achievementsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new AchievementsRepository(achievementsRemoteDataSource, achievementsLocalDataSource);
        }

        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(AchievementsDataSource, AchievementsDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets Achievements from local data source (realm) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadCallback <ArrayList<Achievement>>#onFailure()} is fired if all data sources fail to
     * get the data.
     * </p>
     */
    @Override
    public void loadAchievements(final int categoryId, final int page, final @NonNull LoadCallback<ArrayList<Achievement>> callback) {
        checkNotNull(callback);

        if (this.mCacheIsDirty) { // the cache is dirty so we need to fetch new data from the network
            this.mAchievementsRemoteDataSource.loadAchievements(categoryId, page, new LoadCallback<ArrayList<Achievement>>() {
                @Override
                public void onSuccess(ArrayList<Achievement> achievements) {
                    mAlreadyBeenHere.put(categoryId, false);
                    callback.onSuccess(achievements);

                    saveAchievements(achievements, new SaveCallback<List<Achievement>>() {
                        @Override
                        public void onSuccess(List<Achievement> achievement) {
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
                    mAlreadyBeenHere.put(categoryId, false);
                    callback.onNoMoreData();
                }

                @Override
                public void onFailure(String message) {
                    if (mAlreadyBeenHere.get(categoryId, false)) {
                        callback.onFailure(message);
                        return;
                    }

                    mCacheIsDirty = false;
                    mAlreadyBeenHere.put(categoryId, true);
                    loadAchievements(categoryId, page, callback);
                }
            });

            return;
        }

        // return result by querying the local storage
        mAchievementsLocalDataSource.loadAchievements(categoryId, page, new LoadCallback<ArrayList<Achievement>>() {
            @Override
            public void onSuccess(ArrayList<Achievement> achievements) {
                mAlreadyBeenHere.put(categoryId, false);
                callback.onSuccess(achievements);
            }

            @Override
            public void onNoMoreData() {
                mAlreadyBeenHere.put(categoryId, false);
                callback.onNoMoreData();
            }

            @Override
            public void onFailure(String message) {
                // table is new or empty so load data from remote data source
                refreshCache(); // if no data available make cache dirty in order to fetch data from the network next time
                loadAchievements(categoryId, page, callback);
            }
        });
    }

    /**
     * Gets Achievement from local data source (realm) unless the table is new or empty. In that case it
     * uses the network data source.
     * <p>
     * Note: {@link GetCallback<Achievement>#onFailure()} is fired if both data sources fail to
     * get the data.
     */
    @Override
    public void getAchievement(final int id, final @NonNull GetCallback<Achievement> callback) {
        checkNotNull(callback);

        // todo: implement cache strategy
        // Load from server/persisted
        // Is the task in the local data source? If not, query the network.
        mAchievementsLocalDataSource.getAchievement(id, new GetCallback<Achievement>() {
            @Override
            public void onSuccess(Achievement achievement) {
                callback.onSuccess(achievement);
            }

            @Override
            public void onFailure(final String message) {
                mAchievementsRemoteDataSource.getAchievement(id, new GetCallback<Achievement>() {
                    @Override
                    public void onSuccess(Achievement achievement) {
                        callback.onSuccess(achievement);
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
     * Saves Achievement object to local data source.
     */
    @Override
    public void saveAchievements(@NonNull List<Achievement> achievements, @NonNull SaveCallback<List<Achievement>> callback) {
        this.mAchievementsLocalDataSource.saveAchievements(achievements, callback);
    }

    @Override
    public void refreshCache() {
        this.mCacheIsDirty = true;
    }
}