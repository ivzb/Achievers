package com.achievers.ui.quest;

import android.databinding.Bindable;
import android.databinding.ViewDataBinding;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Quest;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base._contracts.presenters.BaseEndlessAdapterPresenter;
import com.achievers.ui._base._contracts.view_models.BaseEndlessAdapterViewModel;
import com.achievers.ui._base._contracts.views.BaseEndlessAdapterView;

public class QuestContract {

    public interface View<DB extends ViewDataBinding>
            extends BaseEndlessAdapterView<Achievement, QuestContract.Presenter, QuestContract.ViewModel, DB>,
            BaseAdapterActionHandler<Achievement> {

        void openRewardsUi(Quest quest);
    }

    public interface Presenter extends BaseEndlessAdapterPresenter<Achievement> {

        void clickRewards(Quest quest);
    }

    public interface ViewModel extends BaseEndlessAdapterViewModel<Achievement> {

        @Bindable
        Quest getQuest();
        void setQuest(Quest quest);
    }
}