package com.achievers.data.source;

import android.support.annotation.NonNull;

import com.achievers.data.Achievement;
import com.achievers.data.Evidence;
import com.achievers.data.source.callbacks.GetCallback;
import com.achievers.data.source.callbacks.LoadCallback;
import com.achievers.data.source.callbacks.SaveCallback;

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
            @NonNull final LoadCallback<List<Evidence>> callback
    );

    void saveEvidence(
            @NonNull final List<Evidence> evidence,
            @NonNull final SaveCallback<Void> callback
    );

    void refreshCache();
}