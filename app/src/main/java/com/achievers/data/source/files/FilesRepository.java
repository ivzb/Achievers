package com.achievers.data.source.files;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.models.File;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load operator cards from the data sources into a cache.
 * <p>
 * This implements a synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source on every application start.
 */
public class FilesRepository implements FilesDataSource {

    private static FilesDataSource INSTANCE = null;

    private final FilesDataSource mFileRemoteDataSource;

    // Prevent direct instantiation.
    private FilesRepository(@NonNull FilesDataSource fileRemoteDataSource) {
        this.mFileRemoteDataSource = checkNotNull(fileRemoteDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param fileRemoteDataSource the backend data source
     * @return the {@link FilesDataSource} instance
     */
    public static FilesDataSource getInstance(FilesDataSource fileRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FilesRepository(fileRemoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(FilesDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void storeFile(final @NonNull File file, final @NonNull SaveCallback<File> callback) {
        this.mFileRemoteDataSource.storeFile(checkNotNull(file), callback);
    }
}

