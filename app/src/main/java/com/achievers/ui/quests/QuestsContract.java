package com.achievers.ui.quests;

import android.databinding.ViewDataBinding;

import com.achievers.data.entities.Quest;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base._contracts.presenters.BaseEndlessAdapterPresenter;
import com.achievers.ui._base._contracts.view_models.BaseEndlessAdapterViewModel;
import com.achievers.ui._base._contracts.views.BaseEndlessAdapterView;

public class QuestsContract {

    public interface View<DB extends ViewDataBinding>
            extends BaseEndlessAdapterView<Quest, Presenter, ViewModel, DB>,
                    BaseAdapterActionHandler<Quest>, RewardsActionHandler {

        void openRewardsUi(Quest quest);
    }

    public interface Presenter extends BaseEndlessAdapterPresenter<Quest> {

        void clickRewards(Quest quest);
    }

    public interface ViewModel extends BaseEndlessAdapterViewModel<Quest> {

    }
}