package com.achievers.ui._base._contracts.adapters;

public interface BaseSelectableAdapter<T> {

    T getSelection();

    int getSelectedPosition();
    void setSelectedPosition(int position);
}