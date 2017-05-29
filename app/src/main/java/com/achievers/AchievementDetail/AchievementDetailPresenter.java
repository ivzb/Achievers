package com.achievers.AchievementDetail;

import android.support.annotation.NonNull;

import com.achievers.data.Achievement;
import com.achievers.data.Evidence;
import com.achievers.data.source.AchievementsDataSource;
import com.achievers.data.source.AchievementsRepository;
import com.achievers.data.source.EvidenceDataSource;
import com.achievers.data.source.EvidenceRepository;
import com.achievers.data.source.callbacks.GetCallback;

import java.util.List;

/**
 * Listens to user actions from the UI ({@link AchievementDetailFragment}), retrieves the data and updates
 * the UI as required.
 */
public class AchievementDetailPresenter implements AchievementDetailContract.Presenter {

    private final AchievementDetailContract.View mAchievementDetailView;

    private AchievementsRepository mAchievementsRepository;
    private EvidenceRepository mEvidenceRepository;
    private boolean mFirstLoad;

    @NonNull
    private int mAchievementId;

    public AchievementDetailPresenter(int achievementId,
           AchievementsRepository achievementsRepository, EvidenceRepository evidenceRepository,
           @NonNull AchievementDetailContract.View view) {
        this.mAchievementId = achievementId;
        this.mAchievementsRepository = achievementsRepository;
        this.mEvidenceRepository = evidenceRepository;
        this.mAchievementDetailView = view;
        this.mAchievementDetailView.setPresenter(this);
        this.mFirstLoad = true;
    }

    @Override
    public void start() {
        getAchievement();
    }

    @Override
    public void getAchievement() {
        this.mAchievementsRepository.getAchievement(this.mAchievementId, new GetCallback<Achievement>() {
            @Override
            public void onSuccess(final Achievement achievement) {
                // The view may not be able to handle UI updates anymore
                if (!mAchievementDetailView.isActive()) return;

                if (achievement != null) {
                    mAchievementDetailView.showAchievement(achievement);
                    loadEvidence(achievement.getId(), true);
                } else {
                    mAchievementDetailView.showLoadingAchievementError();
                }
            }

            @Override
            public void onFailure(String message) {
                // The view may not be able to handle UI updates anymore
                if (!mAchievementDetailView.isActive()) return;

                mAchievementDetailView.showLoadingAchievementError();
            }
        });
    }

    @Override
    public void loadEvidence(int achievementId, boolean forceUpdate) {
        // a network reload will be forced on first load.
        this.loadEvidence(achievementId, forceUpdate || this.mFirstLoad, true);
        this.mFirstLoad = false;
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link EvidenceDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadEvidence(final int achievementId, boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) mAchievementDetailView.setLoadingIndicator(true);
        if (forceUpdate) mAchievementsRepository.refreshCache();

        this.mEvidenceRepository.loadEvidence(achievementId, new EvidenceDataSource.LoadEvidenceCallback() {
            @Override
            public void onLoaded(final List<Evidence> evidence) {
                // The view may not be able to handle UI updates anymore
                if (!mAchievementDetailView.isActive()) return;
                if (showLoadingUI) mAchievementDetailView.setLoadingIndicator(false);

                mAchievementDetailView.showEvidence(evidence);
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mAchievementDetailView.isActive()) return;
                mAchievementDetailView.showLoadingEvidenceError();
                mAchievementDetailView.setLoadingIndicator(false);
            }
        });
    }
}