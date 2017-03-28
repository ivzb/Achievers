package com.achievers.data.source.remote;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ODataResponseArray<T> {
    @SerializedName("value")
    @NonNull
    List<T> mResult;

    @NonNull
    public List<T> getResult() {
        return mResult;
    }
}