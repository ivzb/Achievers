package com.achievers.AchievementDetail;

import android.support.annotation.NonNull;

import com.achievers.data.Achievement;
import com.achievers.data.source.AchievementsDataSource;
import com.achievers.data.source.AchievementsRepository;

/**
 * Listens to user actions from the UI ({@link AchievementDetailFragment}), retrieves the data and updates
 * the UI as required.
 */
public class AchievementDetailPresenter implements AchievementDetailContract.Presenter {

    private final AchievementDetailContract.View mAchievementDetailView;

    private AchievementsRepository mAchievementsRepository;

    @NonNull
    private int mAchievementId;

    public AchievementDetailPresenter(int achievementId,
           AchievementsRepository achievementsRepository,
           @NonNull AchievementDetailContract.View view) {
        this.mAchievementId = achievementId;
        this.mAchievementsRepository = achievementsRepository;
        this.mAchievementDetailView = view;
        this.mAchievementDetailView.setPresenter(this);
    }


    @Override
    public void start() {
        getAchievement();
    }

    @Override
    public void getAchievement() {
        this.mAchievementsRepository.getAchievement(this.mAchievementId, new AchievementsDataSource.GetAchievementCallback() {
            @Override
            public void onLoaded(final Achievement achievement) {
                // The view may not be able to handle UI updates anymore
                if (achievement != null) {
                    mAchievementDetailView.showAchievement(achievement);
                } else {
                    mAchievementDetailView.showError();
                }
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                mAchievementDetailView.showError();
            }
        });
    }
}