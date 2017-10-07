package com.achievers.ui.add_achievement;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.achievers.BR;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Involvement;
import com.achievers.ui._base.contracts.BaseSelectableAdapter;

public class AddAchievementViewModel
        extends BaseObservable
        implements AddAchievementContract.ViewModel {

    private Achievement mAchievement;
    private BaseSelectableAdapter<Involvement> mInvolvementsAdapter;

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

    @Override
    public BaseSelectableAdapter<Involvement> getInvolvementsAdapter() {
        return mInvolvementsAdapter;
    }

    @Override
    public void setInvolvementsAdapter(BaseSelectableAdapter<Involvement> adapter) {
        mInvolvementsAdapter = adapter;
    }
}