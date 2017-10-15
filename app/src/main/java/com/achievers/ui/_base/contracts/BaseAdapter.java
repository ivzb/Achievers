package com.achievers.ui._base.contracts;

import android.os.Parcelable;

import com.achievers.data.entities._base.BaseModel;

import java.util.List;

public interface BaseAdapter<T extends BaseModel> {

    void add(List<T> entities);

    Parcelable onSaveInstanceState();
    void onRestoreInstanceState(Parcelable parcelable);
}