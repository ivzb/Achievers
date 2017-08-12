package com.achievers.AchievementDetail;

import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

import com.achievers.data.models.Achievement;
import com.achievers.data.models.Evidence;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.evidence.EvidenceDataSource;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.source.RESTClient;

import java.util.List;

/**
 * Listens to user actions from the UI ({@link AchievementDetailFragment}), retrieves the data and updates
 * the UI as required.
 */
public class AchievementDetailPresenter implements AchievementDetailContract.Presenter {

    private final AchievementDetailContract.View mAchievementDetailView;

    private AchievementsDataSource mAchievementsDataSource;
    private EvidenceDataSource mEvidenceDataSource;
    private boolean mFirstLoad;
    private SparseBooleanArray mNoMoreData;
    private SparseIntArray mPages;

    private int mAchievementId;

    public AchievementDetailPresenter(
            int achievementId,
           AchievementsDataSource achievementsDataSource,
           EvidenceDataSource evidenceDataSource,
           @NonNull AchievementDetailContract.View view) {

        this.mAchievementId = achievementId;
        this.mAchievementsDataSource = achievementsDataSource;
        this.mEvidenceDataSource = evidenceDataSource;
        this.mAchievementDetailView = view;

        this.mAchievementDetailView.setPresenter(this);
        this.mFirstLoad = true;
        this.mNoMoreData = new SparseBooleanArray();
        this.mPages = new SparseIntArray();
    }

    @Override
    public void start() {
        getAchievement();
    }

    @Override
    public void getAchievement() {
        this.mAchievementsDataSource.getAchievement(this.mAchievementId, new GetCallback<Achievement>() {
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
        if (this.mNoMoreData.get(achievementId, false)) return; // no more data for this categoryId
        final int currentPage = this.mPages.get(achievementId, 0);

        if (showLoadingUI) mAchievementDetailView.setLoadingIndicator(true);
        if (forceUpdate) mAchievementsDataSource.refreshCache();

        this.mEvidenceDataSource.loadEvidence(achievementId, currentPage, new LoadCallback<List<Evidence>>() {
            @Override
            public void onSuccess(final List<Evidence> evidence) {
                // The view may not be able to handle UI updates anymore
                if (!mAchievementDetailView.isActive()) return;
                if (showLoadingUI) mAchievementDetailView.setLoadingIndicator(false);

//                mAchievementDetailView.showEvidence(evidence);
                mPages.put(achievementId, currentPage + 1); // current category page++
                if (evidence.size() < RESTClient.getPageSize()) mNoMoreData.put(achievementId, true); // no more data for this categoryId

                mAchievementDetailView.showEvidence(evidence);
            }

            @Override
            public void onNoMoreData() {
                if (!mAchievementDetailView.isActive()) return;
                if (showLoadingUI) mAchievementDetailView.setLoadingIndicator(false);

                mNoMoreData.put(achievementId, true); // no more data for this categoryId
            }

            @Override
            public void onFailure(String message) {
                // The view may not be able to handle UI updates anymore
                if (!mAchievementDetailView.isActive()) return;
                mAchievementDetailView.showLoadingEvidenceError();
                mAchievementDetailView.setLoadingIndicator(false);
            }
        });
    }
}