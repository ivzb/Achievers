package com.achievers.ui.achievements;

import android.databinding.ViewDataBinding;

import com.achievers.data.entities.Achievement;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base._contracts.presenters.BaseEndlessAdapterPresenter;
import com.achievers.ui._base._contracts.view_models.BaseEndlessAdapterViewModel;
import com.achievers.ui._base._contracts.views.BaseEndlessAdapterView;

public class AchievementsContract {

    public interface View<DB extends ViewDataBinding>
            extends BaseEndlessAdapterView<Achievement, Presenter, ViewModel, DB>,
                    BaseAdapterActionHandler<Achievement> {

    }

    public interface Presenter extends BaseEndlessAdapterPresenter<Achievement> {

    }

    public interface ViewModel extends BaseEndlessAdapterViewModel<Achievement> {

    }
}