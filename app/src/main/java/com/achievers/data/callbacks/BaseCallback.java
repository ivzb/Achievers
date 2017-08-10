package com.achievers.data.callbacks;

public interface BaseCallback<T> {
    void onSuccess(T data);
    void onFailure(String message);
}