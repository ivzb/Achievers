package com.achievers.ui.achievement;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.achievers.BR;
import com.achievers.ui.evidence.EvidenceAdapter;
import com.achievers.data.models.Achievement;

/**
 * Exposes the data to be used in the {@link AchievementDetailContract.View}.
 * <p>
 * {@link BaseObservable} implements a listener registration mechanism which is notified when a
 * property changes. This is done by assigning a {@link Bindable} annotation to the property's
 * getter method.
 */
public class AchievementDetailViewModel extends BaseObservable {

    private final AchievementDetailContract.Presenter mPresenter;
    private Achievement mAchievement;
    private EvidenceAdapter mEvidenceAdapter;

    private Context mContext;

    AchievementDetailViewModel(Context context, AchievementDetailContract.Presenter presenter) {
        this.mContext = context;
        this.mPresenter = presenter;
    }

    @Bindable
    public Achievement getAchievement() {
        return this.mAchievement;
    }

    @Bindable
    public EvidenceAdapter getEvidenceAdapter() {
        return this.mEvidenceAdapter;
    }

    void setAchievement(Achievement achievement) {
        this.mAchievement = achievement;
        this.notifyPropertyChanged(BR.achievement);
    }

    public void setEvidenceAdapter(EvidenceAdapter evidenceAdapter) {
        this.mEvidenceAdapter = evidenceAdapter;
        notifyPropertyChanged(BR.evidenceAdapter);
    }
}