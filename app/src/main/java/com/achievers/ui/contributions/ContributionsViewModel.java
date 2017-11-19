package com.achievers.ui.contributions;

import android.databinding.BaseObservable;

import com.achievers.data.entities.AchievementProgress;
import com.achievers.ui._base.contracts.adapters.BaseAdapter;

public class ContributionsViewModel
        extends BaseObservable
        implements ContributionsContract.ViewModel {

    private int mPage;
    private BaseAdapter<AchievementProgress> mAdapter;

    @Override
    public int getPage() {
        return mPage;
    }

    @Override
    public void setPage(int page) {
        mPage = page;
    }

    @Override
    public BaseAdapter<AchievementProgress> getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(BaseAdapter<AchievementProgress> adapter) {
        mAdapter = adapter;
    }
}
