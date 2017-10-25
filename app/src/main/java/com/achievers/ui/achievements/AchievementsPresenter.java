package com.achievers.ui.achievements;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.ui._base.AbstractPresenter;
import com.achievers.ui.add_achievement.AddAchievementActivity;

import java.util.List;

import static com.achievers.Config.RECYCLER_INITIAL_PAGE;
import static com.achievers.utils.Preconditions.checkNotNull;

public class AchievementsPresenter
        extends AbstractPresenter<AchievementsContract.View>
        implements AchievementsContract.Presenter {

    private final AchievementsDataSource mDataSource;

    public AchievementsPresenter(
            @NonNull Context context,
            @NonNull AchievementsContract.View view,
            @NonNull AchievementsDataSource dataSource) {

        mContext = checkNotNull(context, "context cannot be null");
        mView = checkNotNull(view, "view cannot be null");
        mDataSource = checkNotNull(dataSource, "achievementsDataSource cannot be null");
    }

    @Override
    public void start() {

    }

    @Override
    public void refresh() {
        loadAchievements(RECYCLER_INITIAL_PAGE);
    }

    @Override
    public void loadAchievements(final int page) {
        if (!mView.isActive()) return;

        mView.setLoadingIndicator(true);

        mDataSource.load(null, page, new LoadCallback<Achievement>() {
            @Override
            public void onSuccess(List<Achievement> data) {
                if (!mView.isActive()) return;

                mView.setPage(page);
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

    @Override
    public void clickAchievement(Achievement achievement) {
        if (!mView.isActive()) return;

        if (achievement == null) {
            mView.showErrorMessage("Missing achievement");
            return;
        }

        mView.openAchievementUi(achievement);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (AddAchievementActivity.REQUEST_ADD_ACHIEVEMENT == requestCode && Activity.RESULT_OK == resultCode) {
            mView.showSuccessfulMessage("Your achievement will be uploaded shortly.");
        }
    }
}
