package com.achievers.ui.achievements;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.achievers.data.entities.Achievement;
import com.achievers.data.sources.achievements.AchievementsDataSource;
import com.achievers.ui._base.presenters.EndlessAdapterPresenter;
import com.achievers.ui.add_achievement.AddAchievementActivity;

public class AchievementsPresenter
        extends EndlessAdapterPresenter<Achievement, AchievementsContract.View, AchievementsDataSource>
        implements AchievementsContract.Presenter {

    AchievementsPresenter(
            @NonNull Context context,
            @NonNull AchievementsContract.View view,
            @NonNull AchievementsDataSource dataSource) {

        super(context, view, dataSource);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (AddAchievementActivity.REQUEST_ADD_ACHIEVEMENT == requestCode && Activity.RESULT_OK == resultCode) {
            mView.showSuccessfulMessage("Your achievement will be uploaded shortly.");
        }
    }
}