package com.achievers.data.callbacks;

import com.achievers.data.Result;

import java.util.List;

public interface LoadCallback<T> {

    void onSuccess(Result<List<T>> data, int page);
    void onNoMore();
    void onFailure(String message);
}