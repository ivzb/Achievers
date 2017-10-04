package com.achievers.ui.achievements;

import android.databinding.BaseObservable;

public class ViewModel
        extends BaseObservable
        implements Contracts.ViewModel {

    private Contracts.Adapter mAdapter;

    @Override
    public Contracts.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(Contracts.Adapter adapter) {
        mAdapter = adapter;
    }
}