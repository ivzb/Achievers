package com.achievers.ui.achievement;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.evidence.EvidenceDataSource;
import com.achievers.ui._base.AbstractPresenter;

public class AchievementPresenter
        extends AbstractPresenter<AchievementContract.View>
        implements AchievementContract.Presenter {

    private final long mAchievementId;

    private final AchievementsDataSource mAchievementsDataSource;
    private final EvidenceDataSource mEvidenceDataSource;

    AchievementPresenter(
           long achievementId,
           @NonNull AchievementContract.View view,
           AchievementsDataSource achievementsDataSource,
           EvidenceDataSource evidenceDataSource) {

        mAchievementId = achievementId;
        mView = view;
        mAchievementsDataSource = achievementsDataSource;
        mEvidenceDataSource = evidenceDataSource;
    }

    @Override
    public void start() {
        getAchievement();
    }

    private void getAchievement() {
        mAchievementsDataSource.getAchievement(mAchievementId, new GetCallback<Achievement>() {
            @Override
            public void onSuccess(final Achievement achievement) {
                if (!mView.isActive()) return;

                if (achievement == null) {
                    mView.showErrorMessage("Could not load achievement.");
                    return;
                }

                mView.showAchievement(achievement);
            }

            @Override
            public void onFailure(String message) {
                if (!mView.isActive()) return;

                mView.showErrorMessage("Could not load achievement.");
            }
        });
    }

    @Override
    public void loadEvidence() {
        // todo
    }
}