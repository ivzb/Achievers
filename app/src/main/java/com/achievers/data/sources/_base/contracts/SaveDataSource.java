package com.achievers.data.sources._base.contracts;

import com.achievers.data.callbacks.SaveCallback;

public interface SaveDataSource<T> {

    void save(T entity, final SaveCallback<String> callback);
}
