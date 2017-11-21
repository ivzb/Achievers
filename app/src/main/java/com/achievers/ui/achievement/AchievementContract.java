package com.achievers.ui.achievement;

import android.databinding.Bindable;
import android.databinding.ViewDataBinding;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Evidence;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base._contracts.adapters.BaseMultimediaAdapter;
import com.achievers.ui._base._contracts.presenters.BaseEndlessAdapterPresenter;
import com.achievers.ui._base._contracts.view_models.BaseEndlessAdapterViewModel;
import com.achievers.ui._base._contracts.views.BaseEndlessAdapterView;

public class AchievementContract {

    public interface View<DB extends ViewDataBinding>
            extends BaseEndlessAdapterView<Evidence, Presenter, ViewModel, DB>,
                    BaseAdapterActionHandler<Evidence> {

    }

    public interface Presenter extends BaseEndlessAdapterPresenter<Evidence> {

    }

    public interface ViewModel extends BaseEndlessAdapterViewModel<Evidence> {

        @Bindable
        Achievement getAchievement();
        void setAchievement(Achievement achievement);

        @Bindable
        BaseMultimediaAdapter<Evidence> getAdapter();
        void setAdapter(BaseMultimediaAdapter<Evidence> adapter);
    }
}