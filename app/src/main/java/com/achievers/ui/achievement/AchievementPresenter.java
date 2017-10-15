package com.achievers.ui.achievement;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Evidence;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.evidences.EvidencesDataSource;
import com.achievers.ui._base.AbstractPresenter;

import java.util.List;

public class AchievementPresenter
        extends AbstractPresenter<AchievementContract.View>
        implements AchievementContract.Presenter {

    private final AchievementsDataSource mAchievementsDataSource;
    private final EvidencesDataSource mEvidencesDataSource;

    AchievementPresenter(
           @NonNull AchievementContract.View view,
           AchievementsDataSource achievementsDataSource,
           EvidencesDataSource evidencesDataSource) {

        mView = view;
        mAchievementsDataSource = achievementsDataSource;
        mEvidencesDataSource = evidencesDataSource;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadEvidences(final long achievementId, final int page) {
        if (!mView.isActive()) return;

        mView.setLoadingIndicator(true);

        mEvidencesDataSource.load(achievementId, page, new LoadCallback<Evidence>() {
            @Override
            public void onSuccess(List<Evidence> data) {
                if (!mView.isActive()) return;

                mView.setPage(page);
                mView.setLoadingIndicator(false);
                mView.showEvidences(data);
            }

            @Override
            public void onFailure(String message) {
                if (!mView.isActive()) return;

                mView.setLoadingIndicator(false);
                mView.showErrorMessage(message);
            }
        });
    }
}