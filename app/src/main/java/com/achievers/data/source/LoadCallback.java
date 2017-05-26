package com.achievers.data.source;

public interface LoadCallback<T> {
    void onSuccess(T data);
    void onNoMoreData();
    void onFailure(String message);
}
