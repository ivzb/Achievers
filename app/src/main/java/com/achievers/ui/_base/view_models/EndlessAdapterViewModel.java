package com.achievers.ui._base.view_models;

import android.databinding.BaseObservable;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.ui._base._contracts.adapters.BaseAdapter;
import com.achievers.ui._base._contracts.view_models.BaseEndlessAdapterViewModel;

public abstract class EndlessAdapterViewModel<T extends BaseModel>
        extends BaseObservable
        implements BaseEndlessAdapterViewModel<T> {

    private int mPage;
    private BaseAdapter<T> mAdapter;

    @Override
    public int getPage() {
        return mPage;
    }

    @Override
    public void setPage(int page) {
        mPage = page;
    }

    @Override
    public BaseAdapter<T> getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(BaseAdapter<T> adapter) {
        mAdapter = adapter;
    }
}