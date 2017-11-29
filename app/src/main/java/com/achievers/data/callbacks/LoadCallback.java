package com.achievers.data.callbacks;

import java.util.List;

public interface LoadCallback<T> {

    void onSuccess(List<T> data, int page);
    void onNoMore();
    void onFailure(String message);
}