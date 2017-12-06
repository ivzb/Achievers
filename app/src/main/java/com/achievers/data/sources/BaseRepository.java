package com.achievers.data.sources;

import android.support.annotation.NonNull;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public abstract class BaseRepository<T> {

    protected final T mRemoteDataSource;
    protected final T mLocalDataSource;

    protected BaseRepository(
            @NonNull T remoteDataSource,
            @NonNull T localDataSource) {

        mRemoteDataSource = checkNotNull(remoteDataSource);
        mLocalDataSource = checkNotNull(localDataSource);
    }
}