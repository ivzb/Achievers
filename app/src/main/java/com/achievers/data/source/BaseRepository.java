package com.achievers.data.source;

import android.support.annotation.NonNull;

import static com.google.android.exoplayer2.util.Assertions.checkNotNull;

public abstract class BaseRepository<T> {

    protected final T mRemoteDataSource;
    protected final T mLocalDataSource;

    protected BaseRepository(@NonNull T remoteDataSource, @NonNull T localDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
        mLocalDataSource = checkNotNull(localDataSource);
    }
}