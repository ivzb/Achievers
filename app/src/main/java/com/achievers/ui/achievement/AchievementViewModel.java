package com.achievers.ui.achievement;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.achievers.BR;
import com.achievers.data.entities.Achievement;
import com.achievers.ui.evidence.EvidenceAdapter;

public class AchievementViewModel
        extends BaseObservable
        implements AchievementContract.ViewModel {

    private Achievement mAchievement;
    private EvidenceAdapter mEvidenceAdapter;

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
    public EvidenceAdapter getEvidenceAdapter() {
        return this.mEvidenceAdapter;
    }

    @Override
    public void setEvidenceAdapter(EvidenceAdapter evidenceAdapter) {
        this.mEvidenceAdapter = evidenceAdapter;
        notifyPropertyChanged(BR.evidenceAdapter);
    }
}