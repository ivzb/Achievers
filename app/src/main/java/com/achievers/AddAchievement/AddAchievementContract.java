package com.achievers.AddAchievement;

import com.achievers.BasePresenter;
import com.achievers.BaseView;
import com.achievers.data.Achievement;
import com.achievers.data.Involvement;

public class AddAchievementContract {

    interface View extends BaseView<Presenter> {

        void showInvolvements(Involvement[] involvements);

        void showInvalidAchievementMessage(final String message);

        void setAchievement(final Achievement achievement);

        void finishActivity();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void saveAchievement(final Achievement achievement);

        boolean validateAchievement(final Achievement achievement);

        void getInvolvements();
    }
}