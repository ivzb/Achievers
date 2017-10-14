package com.achievers.data.source._base;

import com.achievers.data.callbacks.GetCallback;

public interface GetDataSource<T> {

    void get(long id, GetCallback<T> callback);
}