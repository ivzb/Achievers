package com.achievers.data.source._base;

import com.achievers.data.callbacks.LoadCallback;

public interface LoadDataSource<T> {

    void load(Long id, int page, LoadCallback<T> callback);
}