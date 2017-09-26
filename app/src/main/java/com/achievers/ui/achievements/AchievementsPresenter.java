package com.achievers.ui.achievements;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;

import com.achievers.entities.Achievement;
import com.achievers.entities.Category;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.ui.addachievement.AddAchievementActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class AchievementsPresenter implements AchievementsContract.Presenter {

    private final AchievementsDataSource mDataSource;
    private final AchievementsContract.View mView;
    private SparseBooleanArray mNoMoreData;

    AchievementsPresenter(
            @NonNull AchievementsDataSource achievementsDataSource,
            @NonNull AchievementsContract.View achievementsView) {

        this.mDataSource = checkNotNull(achievementsDataSource, "achievementsDataSource cannot be null");
        this.mView = checkNotNull(achievementsView, "achievementsView cannot be null");
        this.mView.setPresenter(this);
        this.mNoMoreData = new SparseBooleanArray();
    }

    // getString(R.string.loading_achievements_error)

//    @Override
//    public void start() {
//        // TODO: pass categoryId instead of null
//        loadAchievements(null, false);
//    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (AddAchievementActivity.REQUEST_ADD_ACHIEVEMENT == requestCode && Activity.RESULT_OK == resultCode) {
            mView.showSuccessfulMessage("Your achievement will be uploaded shortly.");
        }
    }

    @Override
    public void loadAchievements(final Category category) {
        if (category == null) return;

        // todo
//        if (this.mNoMoreData.get(category.getId(), false)) return; // no more data for this categoryId
//        final long currentPage = this.mPages.get(category.getId(), 0);
//
//        if (showLoadingUI) mView.setLoadingIndicator(true);
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
        mView.showAchievementDetailsUi(requestedAchievement.getId());
    }
}
