package com.achievers.data.source.local;


import android.support.annotation.NonNull;

import com.achievers.data.Achievement;
import com.achievers.data.source.AchievementsDataSource;
import com.achievers.data.source.LoadCallback;

import java.util.ArrayList;
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
     * Note: {@link LoadCallback<ArrayList<Achievement>>#onFailure()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void loadAchievements(int categoryId, @NonNull LoadCallback<ArrayList<Achievement>> callback) {
        RealmResults<Achievement> realmResults = this.mRealm
                .where(Achievement.class)
                .equalTo("category.id", categoryId)
                .findAll()
                .sort("createdOn", Sort.DESCENDING);

        ArrayList<Achievement> achievements = (ArrayList<Achievement>) this.mRealm.copyFromRealm(realmResults);

        if (achievements.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onFailure(null);
        } else {
            callback.onSuccess(achievements);
        }
    }

    /**
     * Note: {@link GetAchievementCallback#onDataNotAvailable()} is fired if the {@link Achievement} isn't
     * found.
     */
    @Override
    public void getAchievement(int id, @NonNull GetAchievementCallback callback) {
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
        // Not required because the {@link EvidenceRepository} handles the logic of refreshing the
        // evidence from all the available data sources.
    }

    @Override
    public void saveAchievements(@NonNull final List<Achievement> achievements, @NonNull final SaveAchievementsCallback callback) {
        this.mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(achievements);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess();;
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onError();
            }
        });
    }
}