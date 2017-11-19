package com.achievers.ui.contributions;

import android.databinding.ViewDataBinding;

import com.achievers.data.entities.AchievementProgress;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;
import com.achievers.ui._base.contracts.adapters.BaseAdapter;

import java.util.List;

public class ContributionsContract {

    public interface View<DB extends ViewDataBinding>
            extends BaseView<Presenter, ViewModel, DB> {

        void setLoadingIndicator(boolean active);

        void showAchievementsProgress(
            List<AchievementProgress> achievementsProgress);

        void openAchievementProgressUi(
            AchievementProgress achievementProgress);

        int getPage();
        void setPage(int page);
    }

    public interface Presenter extends BasePresenter {

        void refresh();

        void loadAchievementsProgress(int page);

        void clickAchievementProgress(
            AchievementProgress achievementProgress);
    }

    public interface ViewModel extends BaseViewModel {

        int getPage();
        void setPage(int page);

        BaseAdapter<AchievementProgress> getAdapter();
        void setAdapter(BaseAdapter<AchievementProgress> adapter);
    }
}
