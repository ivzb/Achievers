package com.achievers.ui.achievement;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Evidence;
import com.achievers.data.source.evidences.EvidencesDataSource;
import com.achievers.ui._base.AbstractPresenter;

import java.util.List;

import static com.achievers.Config.RECYCLER_INITIAL_PAGE;
import static com.achievers.utils.Preconditions.checkNotNull;

public class AchievementPresenter
        extends AbstractPresenter<AchievementContract.View>
        implements AchievementContract.Presenter {

    private final EvidencesDataSource mEvidencesDataSource;

    AchievementPresenter(
           @NonNull AchievementContract.View view,
           EvidencesDataSource evidencesDataSource) {

        mView = checkNotNull(view, "view cannot be null");
        mEvidencesDataSource = checkNotNull(evidencesDataSource, "context cannot be null");
    }

    @Override
    public void start() {

    }

    @Override
    public void refresh(long achievementId) {
        loadEvidences(achievementId, RECYCLER_INITIAL_PAGE);
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