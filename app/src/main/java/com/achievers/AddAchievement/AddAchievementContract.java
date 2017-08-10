package com.achievers.AddAchievement;

import android.graphics.Bitmap;

import com.achievers.BasePresenter;
import com.achievers.BaseView;
import com.achievers.data.models.Achievement;
import com.achievers.data.models.File;
import com.achievers.data.models.Involvement;
import com.achievers.data.callbacks.SaveCallback;

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