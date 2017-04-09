package com.achievers.data.source;

import android.support.annotation.NonNull;

import com.achievers.data.Achievement;

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

    // Prevent direct instantiation
    private AchievementsRepository(@NonNull AchievementsDataSource achievementsRemoteDataSource, @NonNull AchievementsDataSource achievementsLocalDataSource) {
        this.mAchievementsRemoteDataSource = checkNotNull(achievementsRemoteDataSource);
        this.mAchievementsLocalDataSource = checkNotNull(achievementsLocalDataSource);
        this.mCacheIsDirty = true;
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
     * Note: {@link LoadAchievementsCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     * </p>
     */
    @Override
    public void loadAchievements(final Integer categoryId, final @NonNull LoadAchievementsCallback callback) {
        checkNotNull(callback);

        if (this.mCacheIsDirty) { // the cache is dirty so we need to fetch new data from the network
            this.mAchievementsRemoteDataSource.loadAchievements(categoryId, new LoadAchievementsCallback() {
                @Override
                public void onLoaded(List<Achievement> achievements) {
                    mCacheIsDirty = false; // cache is clean so the next call will return results form local data source
                    saveAchievements(achievements); // saving results to local data source
                    loadAchievements(categoryId, callback); // recursively call SELF in order to return data from local data source
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });

            return; // stop execution until saved all achievements
        }

        // return result by querying the local storage
        mAchievementsLocalDataSource.loadAchievements(categoryId, new LoadAchievementsCallback() {
            @Override
            public void onLoaded(List<Achievement> achievements) {
                callback.onLoaded(achievements);
            }

            @Override
            public void onDataNotAvailable() {
                mCacheIsDirty = true; // if no data available make cache dirty in order to fetch data from the network next time
                callback.onDataNotAvailable();
            }
        });
    }

    /**
     * Gets Achievement from local data source (realm) unless the table is new or empty. In that case it
     * uses the network data source.
     * <p>
     * Note: {@link GetAchievementCallback#onDataNotAvailable()} is fired if both data sources fail to
     * get the data.
     */
    @Override
    public void getAchievement(@NonNull final Integer achievementId, final @NonNull GetAchievementCallback callback) {
        checkNotNull(callback);

        // Load from server/persisted
        // Is the task in the local data source? If not, query the network.
        mAchievementsLocalDataSource.getAchievement(achievementId, new GetAchievementCallback() {
            @Override
            public void onLoaded(Achievement achievement) {
                callback.onLoaded(achievement);
            }

            @Override
            public void onDataNotAvailable() {
                mAchievementsRemoteDataSource.getAchievement(achievementId, new GetAchievementCallback() {
                    @Override
                    public void onLoaded(Achievement achievement) {
                        callback.onLoaded(achievement);
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
     * Saves Achievement object to local data source.
     */
    public void saveAchievement(@NonNull Achievement achievement) {
        this.mAchievementsLocalDataSource.saveAchievement(achievement);
    }

    @Override
    public void refreshCache() {
        this.mCacheIsDirty = true;
    }

    private void saveAchievements(List<Achievement> achievements) {
        if (achievements.size() == 0) return;

        int lastElementIndex = achievements.size() - 1;
        Achievement achievementToBeSaved = achievements.remove(lastElementIndex);
        this.saveAchievement(achievementToBeSaved);

        this.saveAchievements(achievements);
    }
}