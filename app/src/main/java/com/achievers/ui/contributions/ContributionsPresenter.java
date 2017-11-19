package com.achievers.ui.contributions;

import android.content.Context;
import android.support.annotation.NonNull;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.AchievementProgress;
import com.achievers.data.source.achievements_progress.AchievementsProgressDataSource;
import com.achievers.ui._base.AbstractPresenter;

import java.util.List;

import static com.achievers.Config.RECYCLER_INITIAL_PAGE;
import static com.achievers.utils.Preconditions.checkNotNull;

public class ContributionsPresenter
        extends AbstractPresenter<ContributionsContract.View>
        implements ContributionsContract.Presenter {

    private final AchievementsProgressDataSource mDataSource;

    public ContributionsPresenter(
            @NonNull Context context,
            @NonNull ContributionsContract.View view,
            @NonNull AchievementsProgressDataSource dataSource) {

        mContext = checkNotNull(context, "context cannot be null");
        mView = checkNotNull(view, "view cannot be null");
        mDataSource = checkNotNull(dataSource, "achievementsProgressDataSource cannot be null");
    }

    @Override
    public void start() {

    }

    @Override
    public void refresh() {
        loadAchievementsProgress(RECYCLER_INITIAL_PAGE);
    }

    @Override
    public void loadAchievementsProgress(final int page) {
        if (!mView.isActive()) return;

        mView.setLoadingIndicator(true);

        mDataSource.load(null, page, new LoadCallback<AchievementProgress>() {
            @Override
            public void onSuccess(List<AchievementProgress> data) {
                if (!mView.isActive()) return;

                mView.setPage(page);
                mView.setLoadingIndicator(false);
                mView.showAchievementsProgress(data);
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
    public void clickAchievementProgress(AchievementProgress achievementProgress) {
        if (!mView.isActive()) return;

        if (achievementProgress == null) {
            mView.showErrorMessage("Missing achievement progress");
            return;
        }

        mView.openAchievementProgressUi(achievementProgress);
    }
}
