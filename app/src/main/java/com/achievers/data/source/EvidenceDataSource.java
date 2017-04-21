package com.achievers.data.source;

import android.support.annotation.NonNull;

import com.achievers.data.Achievement;
import com.achievers.data.Evidence;

import java.util.List;

/**
 * Main entry point for accessing Evidence data.
 */
public interface EvidenceDataSource {
    interface GetEvidenceCallback {

        void onLoaded(Evidence evidence);

        void onDataNotAvailable();
    }

    interface LoadEvidenceCallback {

        void onLoaded(List<Evidence> evidence);

        void onDataNotAvailable();
    }

    void getEvidence(@NonNull int id, @NonNull GetEvidenceCallback callback);

    void loadEvidence(int achievementId, @NonNull LoadEvidenceCallback callback);

    void saveEvidence(@NonNull Evidence evidence);

    void refreshCache();
}