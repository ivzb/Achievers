package com.achievers.data.source.callbacks;

public interface LoadCallback<T> extends BaseCallback<T> {
    void onNoMoreData();
}