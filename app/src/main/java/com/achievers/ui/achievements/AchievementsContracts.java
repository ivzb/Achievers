package com.achievers.ui.achievements;

import com.achievers.data.entities.Achievement;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;

import java.util.List;

public class AchievementsContracts {

    public interface View extends BaseView<Presenter, ViewModel>, AchievementsActionHandler {

        void setLoadingIndicator(boolean active);

        void showAchievements(List<Achievement> achievements);

        void openAchievementUi(Achievement achievement);
        void openAddAchievementUi();
    }

    public interface Presenter extends BasePresenter {

//        void result(int requestCode, int resultCode);

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