package com.achievers.ui._base.contracts;

import android.os.Parcelable;

public interface BaseSelectableAdapter<T> {

    T getSelectedValue();

    Parcelable onSaveInstanceState();
    void onRestoreInstanceState(Parcelable state);
}