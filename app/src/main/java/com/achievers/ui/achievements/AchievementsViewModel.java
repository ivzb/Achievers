package com.achievers.ui.achievements;

import android.databinding.BaseObservable;

public class AchievementsViewModel
        extends BaseObservable
        implements AchievementsContracts.ViewModel {

    private AchievementsContracts.Adapter mAdapter;

    @Override
    public AchievementsContracts.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(AchievementsContracts.Adapter adapter) {
        mAdapter = adapter;
    }
}