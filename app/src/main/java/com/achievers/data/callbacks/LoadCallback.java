package com.achievers.data.callbacks;

public interface LoadCallback<T> extends BaseCallback<T> {
    void onNoMoreData();
}