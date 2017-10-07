package com.achievers.ui._base.contracts;

import android.content.Context;

public interface BaseSelectableAdapter<T> {

    Context getContext();
    T getSelectedValue();
}
