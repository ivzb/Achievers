package com.achievers.data.source.local;


import android.support.annotation.NonNull;

import com.achievers.data.Evidence;
import com.achievers.data.source.EvidenceDataSource;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.google.common.base.Preconditions.checkNotNull;

public class EvidenceLocalDataSource implements EvidenceDataSource {

    private static EvidenceLocalDataSource INSTANCE;
    private Realm mRealm;

    // Prevent direct instantiation.
    private EvidenceLocalDataSource(@NonNull Realm realm) {
        checkNotNull(realm);
        this.mRealm = realm;
    }

    public static EvidenceLocalDataSource getInstance(@NonNull Realm realm) {
        if (INSTANCE == null) {
            INSTANCE = new EvidenceLocalDataSource(realm);
        }

        return INSTANCE;
    }

    /**
     * Note: {@link LoadEvidenceCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void loadEvidence(int achievementId, @NonNull LoadEvidenceCallback callback) {
        RealmResults<Evidence> realmResults = this.mRealm
                .where(Evidence.class)
                .equalTo("achievement.id", achievementId)
                .findAll()
                .sort("createdOn", Sort.DESCENDING);

        List<Evidence> evidence = this.mRealm.copyFromRealm(realmResults);

        if (evidence.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onLoaded(evidence);
        }
    }

    /**
     * Note: {@link GetEvidenceCallback#onDataNotAvailable()} is fired if the {@link Evidence} isn't
     * found.
     */
    @Override
    public void getEvidence(@NonNull int id, @NonNull GetEvidenceCallback callback) {
        Evidence evidence = this.mRealm
                .where(Evidence.class)
                .equalTo("id", id)
                .findFirst();

        if (evidence != null) {
            callback.onLoaded(evidence);
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
    public void saveEvidence(@NonNull final List<Evidence> evidence) {
        this.mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(evidence);
            }
        });
    }
}