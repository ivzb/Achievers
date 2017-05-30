package com.achievers.data.source.local;

import android.support.annotation.NonNull;

import com.achievers.data.Evidence;
import com.achievers.data.source.EvidenceDataSource;
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

public class EvidenceLocalDataSource implements EvidenceDataSource {

    private static EvidenceLocalDataSource INSTANCE;
    private final Realm mRealm;
    private final int mPageSize;

    // Prevent direct instantiation.
    private EvidenceLocalDataSource(@NonNull Realm realm) {
        checkNotNull(realm);
        this.mRealm = realm;
        this.mPageSize = RESTClient.getPageSize();
    }

    public static EvidenceLocalDataSource getInstance(@NonNull Realm realm) {
        if (INSTANCE == null) {
            INSTANCE = new EvidenceLocalDataSource(realm);
        }

        return INSTANCE;
    }

    @Override
    public void loadEvidence(
            final int achievementId,
            final int page,
            @NonNull final LoadCallback<List<Evidence>> callback
    ) {
        RealmResults<Evidence> realmResults = this.mRealm
                .where(Evidence.class)
                .equalTo("achievement.id", achievementId)
                .findAllSorted("id", Sort.DESCENDING);

        List<Evidence> results = new ArrayList<>();
        int start = page * mPageSize;
        int end = Math.max(start + mPageSize, realmResults.size());

        for (int i = start; i < end; i++) {
            results.add(realmResults.get(i));
        }

        List<Evidence> evidence = this.mRealm.copyFromRealm(results);

        if (evidence.isEmpty()) {
            callback.onNoMoreData();
            return;
        }

        callback.onSuccess(evidence);
    }

    @Override
    public void getEvidence(
           final int id,
           @NonNull final GetCallback<Evidence> callback
    ) {
        Evidence evidence = this.mRealm
                .where(Evidence.class)
                .equalTo("id", id)
                .findFirst();

        if (evidence == null) {
            callback.onFailure(null);
            return;
        }

        callback.onSuccess(evidence);
    }

    @Override
    public void refreshCache() {
        // Not required because the {@link EvidenceRepository} handles the logic of refreshing the
        // evidence from all the available data sources.
    }

    @Override
    public void saveEvidence(
            @NonNull final List<Evidence> evidence,
            @NonNull final SaveCallback<Void> callback
    ) {
        this.mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(evidence);
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