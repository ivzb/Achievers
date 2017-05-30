package com.achievers.data.source.local;

import android.support.annotation.NonNull;

import com.achievers.data.Achievement;
import com.achievers.data.source.AchievementsDataSource;
import com.achievers.data.source.callbacks.GetCallback;
import com.achievers.data.source.callbacks.LoadCallback;
import com.achievers.data.source.callbacks.SaveCallback;
import com.achievers.data.source.remote.RESTClient;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.google.common.base.Preconditions.checkNotNull;

public class AchievementsLocalDataSource implements AchievementsDataSource {

    private static AchievementsLocalDataSource INSTANCE;
    private final Realm mRealm;
    private final int mPageSize;

    // Prevent direct instantiation.
    private AchievementsLocalDataSource(@NonNull Realm realm) {
        checkNotNull(realm);
        this.mRealm = realm;
        this.mPageSize = RESTClient.getPageSize();
    }

    public static AchievementsLocalDataSource getInstance(@NonNull Realm realm) {
        if (INSTANCE == null) {
            INSTANCE = new AchievementsLocalDataSource(realm);
        }

        return INSTANCE;
    }

    @Override
    public void loadAchievements(
            final int categoryId,
            final int page,
            @NonNull LoadCallback<List<Achievement>> callback
    ) {
        RealmResults<Achievement> realmResults = this.mRealm
                .where(Achievement.class)
                .equalTo("category.id", categoryId)
                .findAllSorted("id", Sort.DESCENDING);

        List<Achievement> results = new ArrayList<>();
        int start = page * mPageSize;
        int end = Math.max(start + mPageSize, realmResults.size());

        for (int i = start; i < end; i++) {
            results.add(realmResults.get(i));
        }

        List<Achievement> achievements = this.mRealm.copyFromRealm(results);

        if (achievements.isEmpty()) {
            callback.onNoMoreData();
            return;
        }

        callback.onSuccess(achievements);
    }

    @Override
    public void getAchievement(
            final int id,
            @NonNull final GetCallback<Achievement> callback
    ) {
        Achievement achievement = this.mRealm
                .where(Achievement.class)
                .equalTo("id", id)
                .findFirst();

        if (achievement == null) {
            callback.onFailure(null);
            return;
        }

        callback.onSuccess(achievement);
    }

    @Override
    public void refreshCache() {
        // Not required because the {@link EvidenceRepository} handles the logic of refreshing the
        // evidence from all the available data sources.
    }

    @Override
    public void saveAchievements(
            @NonNull final List<Achievement> achievements,
            @NonNull final SaveCallback<Void> callback
    ) {
        this.mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(achievements);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess(null);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onFailure(error.getMessage());
            }
        });
    }
}