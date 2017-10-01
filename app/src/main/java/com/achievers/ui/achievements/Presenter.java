package com.achievers.ui.achievements;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.ui.addachievement.AddAchievementActivity;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class Presenter implements Contracts.Presenter {

    private final AchievementsDataSource mDataSource;
    private final Contracts.View mView;

    Presenter(
            @NonNull AchievementsDataSource dataSource,
            @NonNull Contracts.View view) {

        mDataSource = checkNotNull(dataSource, "achievementsDataSource cannot be null");
        mView = checkNotNull(view, "achievementsView cannot be null");
    }

//    @Override
//    public void result(int requestCode, int resultCode) {
//        if (AddAchievementActivity.REQUEST_ADD_ACHIEVEMENT == requestCode && Activity.RESULT_OK == resultCode) {
//            mView.showSuccessfulMessage("Your achievement will be uploaded shortly.");
//        }
//    }

    @Override
    public void loadAchievements(final int page) {
        if (!mView.isActive()) return;

        mView.setLoadingIndicator(true);

        mDataSource.loadAchievements(page, new LoadCallback<Achievement>() {
            @Override
            public void onSuccess(List<Achievement> data) {
                if (!mView.isActive()) return;

                mView.setLoadingIndicator(false);
                mView.showAchievements(data);
            }

            @Override
            public void onFailure(String message) {
                if (!mView.isActive()) return;

                mView.setLoadingIndicator(false);
                mView.showErrorMessage(message);
            }
        });
    }

//    @Override
//    public void openAchievementDetails(@NonNull Achievement requestedAchievement) {
//        checkNotNull(requestedAchievement, "requestedAchievement cannot be null!");
//        mView.showAchievementDetailsUi(/*requestedAchievement.getId()*/);
//    }
}
