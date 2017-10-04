package com.achievers.ui.achievement;

import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.evidence.EvidenceDataSource;
import com.achievers.ui._base.AbstractPresenter;

/**
 * Listens to user actions from the UI ({@link AchievementFragment}), retrieves the data and updates
 * the UI as required.
 */
public class AchievementPresenter
        extends AbstractPresenter<AchievementContract.View>
        implements AchievementContract.Presenter {

    private AchievementsDataSource mAchievementsDataSource;
    private EvidenceDataSource mEvidenceDataSource;
    private boolean mFirstLoad;
    private SparseBooleanArray mNoMoreData;
    private SparseIntArray mPages;

    private int mAchievementId;

    public AchievementPresenter(
            int achievementId,
            @NonNull AchievementContract.View view,
           AchievementsDataSource achievementsDataSource,
           EvidenceDataSource evidenceDataSource) {

        mAchievementId = achievementId;
        mView = view;
        mAchievementsDataSource = achievementsDataSource;
        mEvidenceDataSource = evidenceDataSource;

        mFirstLoad = true;
        mNoMoreData = new SparseBooleanArray();
        mPages = new SparseIntArray();
    }

    @Override
    public void start() {

    }

    @Override
    public void getAchievement() {
        this.mAchievementsDataSource.getAchievement(this.mAchievementId, new GetCallback<Achievement>() {
            @Override
            public void onSuccess(final Achievement achievement) {
                // The view may not be able to handle UI updates anymore
                if (!mView.isActive()) return;

                if (achievement != null) {
                    mView.showAchievement(achievement);
                    loadEvidence(achievement.getId(), true);
                } else {
                    mView.showLoadingAchievementError();
                }
            }

            @Override
            public void onFailure(String message) {
                // The view may not be able to handle UI updates anymore
                if (!mView.isActive()) return;

                mView.showLoadingAchievementError();
            }
        });
    }

    @Override
    public void loadEvidence(long achievementId, boolean forceUpdate) {
        // a network reload will be forced on first load.
//        this.loadEvidence(achievementId, forceUpdate || this.mFirstLoad, true);
        this.mFirstLoad = false;
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link EvidenceDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
//    private void loadEvidence(final long achievementId, boolean forceUpdate, final boolean showLoadingUI) {
//        if (this.mNoMoreData.get(achievementId, false)) return; // no more data for this categoryId
//        final int currentPage = this.mPages.get(achievementId, 0);
//
//        if (showLoadingUI) mAchievementDetailView.setLoadingIndicator(true);
//
//        this.mEvidenceDataSource.loadEvidence(achievementId, currentPage, new LoadCallback<Evidence>() {
//            @Override
//            public void onSuccess(final List<Evidence> evidence) {
//                // The view may not be able to handle UI updates anymore
//                if (!mAchievementDetailView.isActive()) return;
//                if (showLoadingUI) mAchievementDetailView.setLoadingIndicator(false);
//
////                mAchievementDetailView.showEvidence(evidence);
//                mPages.put(achievementId, currentPage + 1); // current category page++
////                if (evidence.size() < RESTClient.getPageSize()) mNoMoreData.put(achievementId, true); // no more data for this categoryId
//
//                mAchievementDetailView.showEvidence(evidence);
//            }
//
//            @Override
//            public void onFailure(String message) {
//                // The view may not be able to handle UI updates anymore
//                if (!mAchievementDetailView.isActive()) return;
//                mAchievementDetailView.showLoadingEvidenceError();
//                mAchievementDetailView.setLoadingIndicator(false);
//            }
//        });
//    }
}