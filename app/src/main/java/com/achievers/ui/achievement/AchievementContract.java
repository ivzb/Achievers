package com.achievers.ui.achievement;

import android.databinding.ViewDataBinding;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Evidence;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;
import com.achievers.ui.evidence.EvidenceAdapter;

import java.util.List;

public interface AchievementContract {

    interface View<DB extends ViewDataBinding> extends BaseView<Presenter, ViewModel, DB> {

        void setLoadingIndicator(boolean active);

        void showAchievement(Achievement achievement);

        void showEvidence(List<Evidence> evidence);

        void showLoadingAchievementError();

        void showLoadingEvidenceError();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void getAchievement();

        void loadEvidence(long achievementId, boolean forceUpdate);
    }

    interface ViewModel extends BaseViewModel {

        Achievement getAchievement();
        void setAchievement(Achievement achievement);

        EvidenceAdapter getEvidenceAdapter();
        void setEvidenceAdapter(EvidenceAdapter evidenceAdapter);
    }
}