package com.achievers.ui.rewards;

import android.databinding.Bindable;
import android.databinding.ViewDataBinding;

import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.ui._base._contracts.presenters.BaseEndlessAdapterPresenter;
import com.achievers.ui._base._contracts.view_models.BaseEndlessAdapterViewModel;
import com.achievers.ui._base._contracts.views.BaseEndlessAdapterView;

public class RewardsContract {

    public interface View<DB extends ViewDataBinding>
            extends BaseEndlessAdapterView<Reward, Presenter, ViewModel, DB> {

    }

    public interface Presenter extends BaseEndlessAdapterPresenter<Reward> {

    }

    public interface ViewModel extends BaseEndlessAdapterViewModel<Reward> {

        @Bindable
        Quest getQuest();
        void setQuest(Quest quest);
    }
}