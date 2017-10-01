package com.achievers.ui.add_achievement;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.achievers.BR;
import com.achievers.data.entities.Achievement;
import com.achievers.ui.add_achievement.Adapters.InvolvementRecyclerViewAdapter;

public class AddAchievementViewModel
        extends BaseObservable
        implements AddAchievementContract.ViewModel {

    private Achievement mAchievement;
    private InvolvementRecyclerViewAdapter mInvolvementsAdapter;

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
    public InvolvementRecyclerViewAdapter getInvolvementsAdapter() {
        return mInvolvementsAdapter;
    }

    @Override
    public void setInvolvementsAdapter(InvolvementRecyclerViewAdapter adapter) {
        mInvolvementsAdapter = adapter;
        this.notifyPropertyChanged(BR.involvementsAdapter);
    }
}