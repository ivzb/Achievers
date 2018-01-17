package com.achievers.data;

import com.google.gson.annotations.SerializedName;

public class Result<T> {

    @SerializedName("message")
    String message;

    @SerializedName("results")
    T results;

    public Result(T results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public T getResults() {
        return results;
    }
}
