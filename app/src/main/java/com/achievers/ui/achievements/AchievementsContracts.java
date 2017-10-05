package com.achievers.ui.achievements;

import android.databinding.ViewDataBinding;

import com.achievers.data.entities.Achievement;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;

import java.util.List;

public class AchievementsContracts {

    public interface View<DB extends ViewDataBinding>
            extends BaseView<Presenter, ViewModel, DB>, AchievementsActionHandler {

        void setLoadingIndicator(boolean active);

        void showAchievements(List<Achievement> achievements);

        void openAchievementUi(Achievement achievement);
        void openAddAchievementUi();
    }

    public interface Presenter extends BasePresenter {

//        void resultPermission(int requestCode, int resultCode);

        void loadAchievements(int page);

        void clickAchievement(Achievement achievement);
        void clickAddAchievement();
    }

    public interface ViewModel extends BaseViewModel {

        AchievementsContracts.Adapter getAdapter();
        void setAdapter(AchievementsContracts.Adapter adapter);
    }

    public interface Adapter {

        void addAchievements(List<Achievement> achievements);
    }
}