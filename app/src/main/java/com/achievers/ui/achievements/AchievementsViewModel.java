package com.achievers.ui.achievements;

import android.databinding.BaseObservable;

import com.achievers.data.entities.Achievement;
import com.achievers.ui._base.contracts.BaseAdapter;

public class AchievementsViewModel
        extends BaseObservable
        implements AchievementsContract.ViewModel {

    private int mPage;
    private BaseAdapter<Achievement> mAdapter;

    @Override
    public int getPage() {
        return mPage;
    }

    @Override
    public void setPage(int page) {
        mPage = page;
    }

    @Override
    public BaseAdapter<Achievement> getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(BaseAdapter<Achievement> adapter) {
        mAdapter = adapter;
    }
}