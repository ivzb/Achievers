package com.achievers.AchievementDetail;

import com.achievers.BasePresenter;
import com.achievers.BaseView;
import com.achievers.data.Achievement;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface AchievementDetailContract {

    interface View extends BaseView<Presenter> {

        void showAchievement(Achievement achievement);

        void showError();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void getAchievement();
    }
}