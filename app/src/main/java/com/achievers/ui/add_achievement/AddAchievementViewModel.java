package com.achievers.ui.add_achievement;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.achievers.BR;
import com.achievers.data.entities.Achievement;
import com.achievers.ui.add_achievement.Adapters.InvolvementAdapter;

public class AddAchievementViewModel
        extends BaseObservable
        implements AddAchievementContract.ViewModel {

    private Achievement mAchievement;
    private InvolvementAdapter mInvolvementsAdapter;

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
    public InvolvementAdapter getInvolvementsAdapter() {
        return mInvolvementsAdapter;
    }

    @Override
    public void setInvolvementsAdapter(InvolvementAdapter adapter) {
        mInvolvementsAdapter = adapter;
        this.notifyPropertyChanged(BR.involvementsAdapter);
    }
}