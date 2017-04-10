package com.achievers.data.source.local;


import android.support.annotation.NonNull;

import com.achievers.data.Achievement;
import com.achievers.data.source.AchievementsDataSource;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.google.common.base.Preconditions.checkNotNull;

public class AchievementsLocalDataSource implements AchievementsDataSource {

    private static AchievementsLocalDataSource INSTANCE;
    private Realm mRealm;

    // Prevent direct instantiation.
    private AchievementsLocalDataSource(@NonNull Realm realm) {
        checkNotNull(realm);
        this.mRealm = realm;
    }

    public static AchievementsLocalDataSource getInstance(@NonNull Realm realm) {
        if (INSTANCE == null) {
            INSTANCE = new AchievementsLocalDataSource(realm);
        }

        return INSTANCE;
    }

    /**
     * Note: {@link LoadAchievementsCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void loadAchievements(Integer categoryId, @NonNull LoadAchievementsCallback callback) {
        RealmResults<Achievement> realmResults = this.mRealm
                .where(Achievement.class)
                .equalTo("category.id", categoryId)
                .findAll()
                .sort("createdOn", Sort.DESCENDING);

        List<Achievement> achievements = this.mRealm.copyFromRealm(realmResults);

        if (achievements.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onLoaded(achievements);
        }
    }

    /**
     * Note: {@link GetAchievementCallback#onDataNotAvailable()} is fired if the {@link Achievement} isn't
     * found.
     */
    @Override
    public void getAchievement(@NonNull Integer id, @NonNull GetAchievementCallback callback) {
        Achievement achievement = this.mRealm
                .where(Achievement.class)
                .equalTo("id", id)
                .findFirst();

        if (achievement != null) {
            callback.onLoaded(achievement);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void refreshCache() {
        // Not required because the {@link CategoriesRepository} handles the logic of refreshing the
        // categories from all the available data sources.
    }

    @Override
    public void saveAchievement(@NonNull final Achievement achievement) {
        this.mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(achievement);
            }
        });
    }
}