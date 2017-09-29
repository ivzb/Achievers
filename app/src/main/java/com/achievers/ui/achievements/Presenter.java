package com.achievers.ui.achievements;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.ui.addachievement.AddAchievementActivity;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class Presenter implements Contracts.Presenter {

    private final AchievementsDataSource mDataSource;
    private final Contracts.View mView;
    private SparseBooleanArray mNoMoreData;

    Presenter(
            @NonNull AchievementsDataSource dataSource,
            @NonNull Contracts.View view) {

        mDataSource = checkNotNull(dataSource, "achievementsDataSource cannot be null");
        mView = checkNotNull(view, "achievementsView cannot be null");
        mNoMoreData = new SparseBooleanArray();
    }

    // getString(R.string.loading_achievements_error)

    @Override
    public void start() {
        loadAchievements();
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (AddAchievementActivity.REQUEST_ADD_ACHIEVEMENT == requestCode && Activity.RESULT_OK == resultCode) {
            mView.showSuccessfulMessage("Your achievement will be uploaded shortly.");
        }
    }

    @Override
    public void loadAchievements() {
        if (!mView.isActive()) return;

        // todo
//        if (this.mNoMoreData.get(category.getId(), false)) return; // no more data for this categoryId
//        final long currentPage = this.mPages.get(category.getId(), 0);
//
        mView.setLoadingIndicator(true);

        mDataSource.loadAchievements(0, new LoadCallback<Achievement>() {
            @Override
            public void onSuccess(List<Achievement> data) {
                if (!mView.isActive()) return;

                mView.setLoadingIndicator(false);
                mView.showAchievements(data);
            }

            @Override
            public void onFailure(String message) {
                if (!mView.isActive()) return;

                mView.setLoadingIndicator(false);
                mView.showErrorMessage(message);
            }
        });
//
//        mDataSource.loadAchievements(category.getId(), currentPage, new LoadCallback<Achievement>() {
//            @Override
//            public void onSuccess(final List<Achievement> achievements) {
//                // The view may not be able to handle UI updates anymore
//                if (!mView.isActive()) return;
//                if (showLoadingUI) mView.setLoadingIndicator(false);
//
//                mPages.put(category.getId(), currentPage + 1); // current category page++
////                if (achievements.size() < RESTClient.getPageSize()) mNoMoreData.put(category.getId(), true); // no more data for this categoryId
//
//                mView.showAchievements(category, achievements);
//            }
//
//            @Override
//            public void onFailure(String message) {
//                // The view may not be able to handle UI updates anymore
//                if (!mView.isActive()) return;
//                mView.showLoadingError();
//                if (showLoadingUI) mView.setLoadingIndicator(false);
//            }
//        });
    }

    @Override
    public void openAchievementDetails(@NonNull Achievement requestedAchievement) {
        checkNotNull(requestedAchievement, "requestedAchievement cannot be null!");
        mView.showAchievementDetailsUi(/*requestedAchievement.getId()*/);
    }
}
