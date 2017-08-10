package com.achievers.data.source.evidence;

import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;

import com.achievers.data.models.Evidence;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;

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
    private SparseBooleanArray mAlreadyBeenHere;

    // Prevent direct instantiation
    private EvidenceRepository(
            @NonNull EvidenceDataSource evidenceRemoteDataSource,
            @NonNull EvidenceDataSource evidenceLocalDataSource
    ) {
        this.mEvidenceRemoteDataSource = checkNotNull(evidenceRemoteDataSource);
        this.mEvidenceLocalDataSource = checkNotNull(evidenceLocalDataSource);
        this.refreshCache();
        this.mAlreadyBeenHere = new SparseBooleanArray();
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param evidenceRemoteDataSource the backend data source
     * @param evidenceLocalDataSource  the device storage data source
     * @return the {@link EvidenceRepository} instance
     */
    public static EvidenceRepository getInstance(
            EvidenceDataSource evidenceRemoteDataSource,
            EvidenceDataSource evidenceLocalDataSource
    ) {
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
     * Note: {@link LoadCallback<List<Evidence>>#onFailure()} is fired if all data sources fail to
     * get the data.
     * </p>
     */
    @Override
    public void loadEvidence(
            final int achievementId,
            final int page,
            final @NonNull LoadCallback<List<Evidence>> callback
    ) {
        checkNotNull(callback);

        if (this.mCacheIsDirty) { // the cache is dirty so we need to fetch new data from the network
            this.mEvidenceRemoteDataSource.loadEvidence(achievementId, page, new LoadCallback<List<Evidence>>() {
                @Override
                public void onSuccess(List<Evidence> evidence) {
                    mAlreadyBeenHere.put(achievementId, false);
                    callback.onSuccess(evidence);

                    saveEvidence(evidence, new SaveCallback<Void>() {
                        @Override
                        public void onSuccess(Void data) {
                            mCacheIsDirty = false; // cache is clean so the next call will return results form local data source
                        }

                        @Override
                        public void onFailure(String message) {
                            refreshCache();
                        }
                    });
                }

                @Override
                public void onNoMoreData() {
                    mAlreadyBeenHere.put(achievementId, false);
                    callback.onNoMoreData();
                }

                @Override
                public void onFailure(String message) {
                    if (mAlreadyBeenHere.get(achievementId, false)) {
                        mAlreadyBeenHere.put(achievementId, false);
                        callback.onFailure(message);
                        return;
                    }

                    mCacheIsDirty = false;
                    mAlreadyBeenHere.put(achievementId, true);
                    loadEvidence(achievementId, page, callback);
                }
            });

            return;
        }

        // return result by querying the local storage
        mEvidenceLocalDataSource.loadEvidence(achievementId, page, new LoadCallback<List<Evidence>>() {
            @Override
            public void onSuccess(List<Evidence> evidence) {
                mAlreadyBeenHere.put(achievementId, false);
                callback.onSuccess(evidence);
            }

            @Override
            public void onNoMoreData() {
                mAlreadyBeenHere.put(achievementId, false);
                callback.onNoMoreData();
            }

            @Override
            public void onFailure(String message) {
                // table is new or empty so load data from remote data source
                refreshCache(); // if no data available make cache dirty in order to fetch data from the network next time
                mAlreadyBeenHere.put(achievementId, true);
                loadEvidence(achievementId, page, callback);
            }
        });
    }

    @Override
    public void getEvidence(
            final int id,
            final @NonNull GetCallback<Evidence> callback
    ) {
        checkNotNull(callback);

        // todo: implement cache strategy
        // Load from server/persisted
        // Is the task in the local data source? If not, query the network.
        mEvidenceLocalDataSource.getEvidence(id, new GetCallback<Evidence>() {
            @Override
            public void onSuccess(Evidence evidence) {
                callback.onSuccess(evidence);
            }

            @Override
            public void onFailure(final String message) {
                mEvidenceRemoteDataSource.getEvidence(id, new GetCallback<Evidence>() {
                    @Override
                    public void onSuccess(Evidence evidence) {
                        callback.onSuccess(evidence);
                    }

                    @Override
                    public void onFailure(String message) {
                        callback.onFailure(message);
                    }
                });
            }
        });
    }

    /**
     * Saves Evidence object to local data source.
     */
    @Override
    public void saveEvidence(
            @NonNull final List<Evidence> evidence,
            @NonNull final SaveCallback<Void> callback
    ) {
        this.mEvidenceLocalDataSource.saveEvidence(evidence, callback);
    }

    @Override
    public void refreshCache() {
        this.mCacheIsDirty = true;
    }
}