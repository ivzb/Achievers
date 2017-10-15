package com.achievers.ui.achievement;

import android.databinding.Bindable;
import android.databinding.ViewDataBinding;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Evidence;
import com.achievers.ui._base.contracts.BaseAdapter;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;

import java.util.List;

public interface AchievementContract {

    interface View<DB extends ViewDataBinding> extends BaseView<Presenter, ViewModel, DB> {

        void setLoadingIndicator(boolean active);

        void showEvidences(List<Evidence> evidences);

        int getPage();
        void setPage(int page);
    }

    interface Presenter extends BasePresenter {

        void loadEvidences(long achievementId, int page);
    }

    interface ViewModel extends BaseViewModel {

        int getPage();
        void setPage(int page);

        @Bindable
        Achievement getAchievement();
        void setAchievement(Achievement achievement);

        @Bindable
        BaseAdapter<Evidence> getAdapter();
        void setAdapter(BaseAdapter<Evidence> adapter);
    }
}