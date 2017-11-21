package com.achievers.data.source._base.contracts;

import com.achievers.data.callbacks.SaveCallback;

public interface SaveDataSource<T> {

    void save(T t, final SaveCallback<Long> callback);
}
