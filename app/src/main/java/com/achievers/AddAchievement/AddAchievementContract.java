package com.achievers.AddAchievement;

import android.view.View.OnClickListener;

import com.achievers.BasePresenter;
import com.achievers.BaseView;
import com.achievers.data.Achievement;
import com.achievers.data.Involvement;

import java.util.List;

public class AddAchievementContract {

    interface View extends BaseView<Presenter> {

        void showInvolvement(List<Involvement> involvement);

        void showInvalidAchievementMessage(final String message);

        void setViewModel(final AddAchievementViewModel viewModel);

        void finishActivity();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void saveAchievement(final Achievement achievement);

        boolean validateAchievement(final Achievement achievement);

        void getInvolvements();
    }
}