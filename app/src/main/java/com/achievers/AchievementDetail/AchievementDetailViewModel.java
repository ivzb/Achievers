package com.achievers.AchievementDetail;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.achievers.BR;
import com.achievers.data.Achievement;

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

    private Context mContext;

    AchievementDetailViewModel(Context context, AchievementDetailContract.Presenter presenter) {
        this.mContext = context;
        this.mPresenter = presenter;
    }

    @Bindable
    public Achievement getAchievement() {
        return this.mAchievement;
    }

    void setAchievement(Achievement achievement) {
        this.mAchievement = achievement;
        this.notifyPropertyChanged(BR.achievement);
    }
}