package com.achievers.AchievementDetail;

import com.achievers.BasePresenter;
import com.achievers.BaseView;
import com.achievers.data.Achievement;
import com.achievers.data.Evidence;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface AchievementDetailContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showAchievement(Achievement achievement);

        void showEvidence(List<Evidence> evidence);

        void showLoadingAchievementError();

        void showLoadingEvidenceError();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void getAchievement();

        void loadEvidence(int achievementId, boolean forceUpdate);
    }
}