package com.achievers.ui._base.contracts;

public interface BaseSelectableAdapter<T> {

    T getSelection();

    int getSelectedPosition();
    void setSelectedPosition(int position);
}