package com.achievers.ui.achievement;

import android.databinding.Bindable;
import android.databinding.ViewDataBinding;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Evidence;
import com.achievers.ui._base._contracts.BasePresenter;
import com.achievers.ui._base._contracts.BaseView;
import com.achievers.ui._base._contracts.BaseViewModel;
import com.achievers.ui._base._contracts.adapters.BaseMultimediaAdapter;

import java.util.List;

public interface AchievementContract {

    interface View<DB extends ViewDataBinding> extends BaseView<Presenter, ViewModel, DB> {

        void setLoadingIndicator(boolean active);

        void showEvidences(List<Evidence> evidences);

        int getPage();
        void setPage(int page);
    }

    interface Presenter extends BasePresenter {

        void refresh(long achievementId);

        void loadEvidences(long achievementId, int page);
    }

    interface ViewModel extends BaseViewModel {

        int getPage();
        void setPage(int page);

        @Bindable
        Achievement getAchievement();
        void setAchievement(Achievement achievement);

        @Bindable
        BaseMultimediaAdapter<Evidence> getAdapter();
        void setAdapter(BaseMultimediaAdapter<Evidence> adapter);
    }
}