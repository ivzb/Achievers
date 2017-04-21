package com.achievers.data.source;

import android.support.annotation.NonNull;

import com.achievers.data.Evidence;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load Evidence from the data sources.
 * <p>
 * This implements a synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source on every application start.
 * </p>
 */
public class EvidenceRepository implements EvidenceDataSource {

    private static EvidenceRepository INSTANCE = null;
    private final EvidenceDataSource mEvidenceRemoteDataSource;
    private final EvidenceDataSource mEvidenceLocalDataSource;
    private boolean mCacheIsDirty;

    // Prevent direct instantiation
    private EvidenceRepository(@NonNull EvidenceDataSource evidenceRemoteDataSource, @NonNull EvidenceDataSource evidenceLocalDataSource) {
        this.mEvidenceRemoteDataSource = checkNotNull(evidenceRemoteDataSource);
        this.mEvidenceLocalDataSource = checkNotNull(evidenceLocalDataSource);
        this.mCacheIsDirty = true;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param evidenceRemoteDataSource the backend data source
     * @param evidenceLocalDataSource  the device storage data source
     * @return the {@link EvidenceRepository} instance
     */
    public static EvidenceRepository getInstance(EvidenceDataSource evidenceRemoteDataSource, EvidenceDataSource evidenceLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new EvidenceRepository(evidenceRemoteDataSource, evidenceLocalDataSource);
        }

        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(EvidenceDataSource, EvidenceDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets Evidence from local data source (realm) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadEvidenceCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     * </p>
     */
    @Override
    public void loadEvidence(final int achievementId, final @NonNull LoadEvidenceCallback callback) {
        checkNotNull(callback);

        if (this.mCacheIsDirty) { // the cache is dirty so we need to fetch new data from the network
            this.mEvidenceRemoteDataSource.loadEvidence(achievementId, new LoadEvidenceCallback() {
                @Override
                public void onLoaded(List<Evidence> evidence) {
                    mCacheIsDirty = false; // cache is clean so the next call will return results form local data source
                    saveEvidence(evidence); // saving results to local data source
                    loadEvidence(achievementId, callback); // recursively call SELF in order to return data from local data source
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });

            return; // stop execution until saved all evidence
        }

        // return result by querying the local storage
        mEvidenceLocalDataSource.loadEvidence(achievementId, new LoadEvidenceCallback() {
            @Override
            public void onLoaded(List<Evidence> evidence) {
                callback.onLoaded(evidence);
            }

            @Override
            public void onDataNotAvailable() {
                mCacheIsDirty = true; // if no data available make cache dirty in order to fetch data from the network next time
                callback.onDataNotAvailable();
            }
        });
    }

    /**
     * Gets Evidence from local data source (realm) unless the table is new or empty. In that case it
     * uses the network data source.
     * <p>
     * Note: {@link GetEvidenceCallback#onDataNotAvailable()} is fired if both data sources fail to
     * get the data.
     */
    @Override
    public void getEvidence(@NonNull final int id, final @NonNull GetEvidenceCallback callback) {
        checkNotNull(callback);

        // Load from server/persisted
        // Is the task in the local data source? If not, query the network.
        mEvidenceLocalDataSource.getEvidence(id, new GetEvidenceCallback() {
            @Override
            public void onLoaded(Evidence evidence) {
                callback.onLoaded(evidence);
            }

            @Override
            public void onDataNotAvailable() {
                mEvidenceRemoteDataSource.getEvidence(id, new GetEvidenceCallback() {
                    @Override
                    public void onLoaded(Evidence evidence) {
                        callback.onLoaded(evidence);
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
     * Saves Evidence object to local data source.
     */
    public void saveEvidence(@NonNull Evidence evidence) {
        this.mEvidenceLocalDataSource.saveEvidence(evidence);
    }

    @Override
    public void refreshCache() {
        this.mCacheIsDirty = true;
    }

    private void saveEvidence(List<Evidence> evidence) {
        if (evidence.size() == 0) return;

        int lastElementIndex = evidence.size() - 1;
        Evidence evidenceToBeSaved = evidence.remove(lastElementIndex);
        this.saveEvidence(evidenceToBeSaved);

        this.saveEvidence(evidence);
    }
}