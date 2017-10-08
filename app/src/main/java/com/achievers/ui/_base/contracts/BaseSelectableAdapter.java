package com.achievers.ui._base.contracts;

import android.os.Parcelable;

public interface BaseSelectableAdapter<T> {

    T getSelected();

    Parcelable onSaveInstanceState();
    void onRestoreInstanceState(Parcelable state);
}