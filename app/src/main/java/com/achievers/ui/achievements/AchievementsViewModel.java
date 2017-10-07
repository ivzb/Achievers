package com.achievers.ui.achievements;

import android.databinding.BaseObservable;

public class AchievementsViewModel
        extends BaseObservable
        implements AchievementsContracts.ViewModel {

    private int mPage;
    private AchievementsContracts.Adapter mAdapter;

    @Override
    public int getPage() {
        return mPage;
    }

    @Override
    public void setPage(int page) {
        mPage = page;
    }

    @Override
    public AchievementsContracts.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(AchievementsContracts.Adapter adapter) {
        mAdapter = adapter;
    }
}