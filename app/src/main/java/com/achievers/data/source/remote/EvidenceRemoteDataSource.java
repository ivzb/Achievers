package com.achievers.data.source.remote;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.achievers.data.Achievement;
import com.achievers.data.Evidence;
import com.achievers.data.EvidenceType;
import com.achievers.data.source.EvidenceDataSource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.bloco.faker.Faker;
import io.realm.Realm;

/**
 * Implementation of remote network data source.
 */
public class EvidenceRemoteDataSource implements EvidenceDataSource {

    private static EvidenceRemoteDataSource INSTANCE;
    private static final int SERVICE_LATENCY_IN_MILLIS = 500;

    // for developing purposes I am not fetching data from web service
    private final static Map<Integer, Evidence> EVIDENCE_SERVICE_DATA;

    static {
        EVIDENCE_SERVICE_DATA = new LinkedHashMap<>();
    }

    private static void generateEvidence(int count, final Achievement achievement, Faker faker)
    {
        if (count == 0) return;

        Evidence newEvidence = new Evidence(
                EVIDENCE_SERVICE_DATA.size() + 1, faker.lorem.sentence(5), EvidenceType.getRandomEvidenceType(),
                faker.lorem.paragraph(), achievement, faker.date.backward());

        EVIDENCE_SERVICE_DATA.put(newEvidence.getId(), newEvidence);

        generateEvidence(--count, achievement, faker);
    }

    public static EvidenceRemoteDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new EvidenceRemoteDataSource();
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private EvidenceRemoteDataSource() { }

    /**
     * Note: {@link LoadEvidenceCallback#onDataNotAvailable()} is fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void loadEvidence(final int achievementId, final @NonNull LoadEvidenceCallback callback) {
        EVIDENCE_SERVICE_DATA.clear();

        Realm realm = Realm.getDefaultInstance();
        Achievement achievement = realm.where(Achievement.class).equalTo("id", achievementId).findFirst();
        realm.close();

        generateEvidence(15, achievement, new Faker());

        List<Evidence> evidenceToShow = new ArrayList<>(EVIDENCE_SERVICE_DATA.values());
        callback.onLoaded(evidenceToShow);
    }

    /**
     * Note: {@link GetEvidenceCallback#onDataNotAvailable()} is fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void getEvidence(@NonNull int id, final @NonNull GetEvidenceCallback callback) {
        final Evidence evidence = EVIDENCE_SERVICE_DATA.get(id);

        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onLoaded(evidence);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void saveEvidence(@NonNull Evidence evidence) {
        // not implemented yet
    }

    @Override
    public void refreshCache() {
        // Not required because the {@link EvidenceRepository} handles the logic of refreshing the
        // Evidence from all the available data sources.
    }
}
