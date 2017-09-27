package com.achievers.ui.addachievement;

import android.graphics.Bitmap;

import com.achievers.ui.base.BasePresenter;
import com.achievers.ui.base.BaseView;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.File;
import com.achievers.data.entities.Involvement;

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

        void uploadImage(Bitmap bitmap, SaveCallback<File> callback);
    }
}