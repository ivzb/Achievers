package com.achievers.data.source.evidence;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.entities.Evidence;

import java.util.List;

/**
 * Main entry point for accessing Evidence data.
 */
public interface EvidenceDataSource {

    void getEvidence(
            final int id,
            @NonNull final GetCallback<Evidence> callback
    );

    void loadEvidence(
            final int achievementId,
            final int page,
            @NonNull final LoadCallback<Evidence> callback
    );

    void saveEvidence(
            @NonNull final List<Evidence> evidence,
            @NonNull final SaveCallback<Void> callback
    );

    void refreshCache();
}