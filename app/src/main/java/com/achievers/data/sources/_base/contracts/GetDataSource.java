package com.achievers.data.sources._base.contracts;

import com.achievers.data.callbacks.GetCallback;

public interface GetDataSource<T> {

    void get(String id, GetCallback<T> callback);
}