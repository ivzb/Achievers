package com.achievers.ui.achievement;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.achievers.BR;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Evidence;
import com.achievers.ui._base._contracts.adapters.BaseMultimediaAdapter;

public class AchievementViewModel
        extends BaseObservable
        implements AchievementContract.ViewModel {

    private Achievement mAchievement;
    private BaseMultimediaAdapter<Evidence> mAdapter;
    private int mPage;

    AchievementViewModel(Achievement achievement) {
        setAchievement(achievement);
    }

    @Override
    public int getPage() {
        return mPage;
    }

    @Override
    public void setPage(int page) {
        mPage = page;
    }

    @Bindable
    @Override
    public Achievement getAchievement() {
        return this.mAchievement;
    }

    @Override
    public void setAchievement(Achievement achievement) {
        this.mAchievement = achievement;
        this.notifyPropertyChanged(BR.achievement);
    }

    @Bindable
    @Override
    public BaseMultimediaAdapter<Evidence> getAdapter() {
        return this.mAdapter;
    }

    @Override
    public void setAdapter(BaseMultimediaAdapter<Evidence> adapter) {
        this.mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }
}