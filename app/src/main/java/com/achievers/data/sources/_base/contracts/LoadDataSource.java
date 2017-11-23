package com.achievers.data.sources._base.contracts;

import com.achievers.data.callbacks.LoadCallback;

public interface LoadDataSource<T> {

    void load(Long id, int page, LoadCallback<T> callback);
}