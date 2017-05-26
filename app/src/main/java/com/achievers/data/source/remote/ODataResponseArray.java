package com.achievers.data.source.remote;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ODataResponseArray<T> {
    @SerializedName("value")
    @NonNull
    ArrayList<T> mResult;

    @NonNull
    public ArrayList<T> getResult() {
        return mResult;
    }
}