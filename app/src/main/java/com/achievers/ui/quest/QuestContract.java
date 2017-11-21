package com.achievers.ui.quest;

import android.databinding.Bindable;
import android.databinding.ViewDataBinding;

import com.achievers.data.entities.Quest;
import com.achievers.ui._base._contracts.BasePresenter;
import com.achievers.ui._base._contracts.BaseView;
import com.achievers.ui._base._contracts.BaseViewModel;

public interface QuestContract {

    interface View<DB extends ViewDataBinding> extends BaseView<QuestContract.Presenter, QuestContract.ViewModel, DB> {


        void finish();
    }

    interface Presenter extends BasePresenter {


    }

    interface ViewModel extends BaseViewModel {

        @Bindable
        Quest getQuest();
        void setQuest(Quest quest);
    }
}