package com.achievers.ui.achievement;

import com.achievers.ui.base.BasePresenter;
import com.achievers.ui.base.BaseView;
import com.achievers.models.Achievement;
import com.achievers.models.Evidence;

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