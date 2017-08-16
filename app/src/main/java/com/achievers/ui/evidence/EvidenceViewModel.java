package com.achievers.ui.evidence;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.achievers.ui.achievement.AchievementDetailContract;
import com.achievers.BR;
import com.achievers.models.Achievement;

/**
 * Exposes the data to be used in the {@link AchievementDetailContract.View}.
 * <p>
 * {@link BaseObservable} implements a listener registration mechanism which is notified when a
 * property changes. This is done by assigning a {@link Bindable} annotation to the property's
 * getter method.
 */
public class EvidenceViewModel extends BaseObservable {

    private EvidenceAdapter mAdapter;
    private Achievement mAchievement;

    private Context mContext;

    public EvidenceViewModel(Context context) {
        this.mContext = context;
    }

    @Bindable
    public EvidenceAdapter getAdapter() {
        return this.mAdapter;
    }

    @Bindable
    public Achievement getAchievement()
    {
        return this.mAchievement;
    }

    public void setAdapter(EvidenceAdapter adapter) {
        this.mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    public void setAchievement(Achievement achievement) {
        this.mAchievement = achievement;
        notifyPropertyChanged(BR.achievement);
    }
}