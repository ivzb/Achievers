package com.achievers.ui.rewards;

import android.databinding.Bindable;

import com.achievers.BR;
import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.ui._base.view_models.EndlessAdapterViewModel;

class RewardsViewModel
        extends EndlessAdapterViewModel<Reward>
        implements RewardsContract.ViewModel {

    private Quest mQuest;

    RewardsViewModel(Quest quest) {
        setQuest(quest);
    }

    @Bindable
    @Override
    public Quest getQuest() {
        return this.mQuest;
    }

    @Override
    public void setQuest(Quest quest) {
        this.mQuest = quest;
        this.notifyPropertyChanged(BR.quest);
    }

    @Override
    public Long getContainerId() {
        return null;
    }
}