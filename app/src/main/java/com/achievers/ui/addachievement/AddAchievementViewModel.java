package com.achievers.ui.addachievement;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.achievers.ui.addachievement.Adapters.InvolvementRecyclerViewAdapter;
import com.achievers.BR;
import com.achievers.data.entities.Achievement;

/**
 * Exposes the data to be used in the {@link AddAchievementContract.View}.
 * <p>
 * {@link BaseObservable} implements a listener registration mechanism which is notified when a
 * property changes. This is done by assigning a {@link Bindable} annotation to the property's
 * getter method.
 */
public class AddAchievementViewModel extends BaseObservable {

    private final AddAchievementContract.Presenter mPresenter;
    private Achievement mAchievement;
    private InvolvementRecyclerViewAdapter mInvolvementRecyclerViewAdapter;

    private Context mContext;

    AddAchievementViewModel(Context context, AddAchievementContract.Presenter presenter) {
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

    @Bindable
    public InvolvementRecyclerViewAdapter getInvolvementRecyclerViewAdapter() {
        return this.mInvolvementRecyclerViewAdapter;
    }

    void setInvolvementRecyclerViewAdapter(InvolvementRecyclerViewAdapter involvementRecyclerViewAdapter) {
        this.mInvolvementRecyclerViewAdapter = involvementRecyclerViewAdapter;
        this.notifyPropertyChanged(BR.involvementRecyclerViewAdapter);
    }
}