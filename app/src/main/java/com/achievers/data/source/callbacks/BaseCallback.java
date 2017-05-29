package com.achievers.data.source.callbacks;

public interface BaseCallback<T> {
    void onSuccess(T data);
    void onFailure(String message);
}