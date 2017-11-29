package com.achievers.ui.achievement;

import android.databinding.Bindable;

import com.achievers.BR;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Evidence;
import com.achievers.ui._base._contracts.adapters.BaseMultimediaAdapter;
import com.achievers.ui._base.view_models.EndlessAdapterViewModel;

class AchievementViewModel
        extends EndlessAdapterViewModel<Evidence>
        implements AchievementContract.ViewModel {

    private Achievement mAchievement;
    private BaseMultimediaAdapter<Evidence> mAdapter;

    AchievementViewModel(Achievement achievement) {
        setAchievement(achievement);
    }

    @Bindable
    @Override
    public Achievement getAchievement() {
        return mAchievement;
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

    @Override
    public Long getContainerId() {
        return getAchievement().getId();
    }
}