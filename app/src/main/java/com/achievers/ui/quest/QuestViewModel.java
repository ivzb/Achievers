package com.achievers.ui.quest;

import android.databinding.Bindable;

import com.achievers.BR;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Quest;
import com.achievers.ui._base.view_models.EndlessAdapterViewModel;

class QuestViewModel
        extends EndlessAdapterViewModel<Achievement>
        implements QuestContract.ViewModel {

    private Quest mQuest;

    QuestViewModel(Quest quest) {
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
}