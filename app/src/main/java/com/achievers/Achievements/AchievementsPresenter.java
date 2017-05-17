package com.achievers.Achievements;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.achievers.AchievementDetail.AchievementDetailActivity;
import com.achievers.data.Achievement;
import com.achievers.data.Category;
import com.achievers.data.source.AchievementsDataSource;
import com.achievers.data.source.AchievementsRepository;
import com.achievers.data.source.CategoriesDataSource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class AchievementsPresenter implements AchievementsContract.Presenter {

    private final AchievementsRepository mAchievementsRepository;
    private final AchievementsContract.View mAchievementsView;
    private boolean mFirstLoad;

    public AchievementsPresenter(@NonNull AchievementsRepository achievementsRepository,
                                 @NonNull AchievementsContract.View achievementsView) {
        this.mAchievementsRepository = checkNotNull(achievementsRepository, "achievementsRepository cannot be null");
        this.mAchievementsView = checkNotNull(achievementsView, "achievementsView cannot be null");
        this.mAchievementsView.setPresenter(this);
        this.mFirstLoad = true;
    }

//    @Override
//    public void start() {
//        // TODO: pass categoryId instead of null
//        loadAchievements(null, false);
//    }

    @Override
    public void result(int requestCode, int resultCode) {
        // If a Achievement was successfully added, show snackbar
//        if (AddEditAchievementActivity.REQUEST_ADD_ACHIEVEMENT == requestCode && Activity.RESULT_OK == resultCode) {
//            mAchievementsView.showSuccessfullySavedMessage();
//        }
    }

    @Override
    public void loadAchievements(final Category category, boolean forceUpdate) {
        // a network reload will be forced on first load.
        this.loadAchievements(category, forceUpdate || this.mFirstLoad, true);
        this.mFirstLoad = false;
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link CategoriesDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadAchievements(final Category category, boolean forceUpdate, final boolean showLoadingUI) {
        if (category == null) return;
        if (showLoadingUI) mAchievementsView.setLoadingIndicator(true);
        if (forceUpdate) mAchievementsRepository.refreshCache();

        mAchievementsRepository.loadAchievements(category.getId(), new AchievementsDataSource.LoadAchievementsCallback() {
            @Override
            public void onLoaded(final List<Achievement> achievements) {
                // The view may not be able to handle UI updates anymore
                if (!mAchievementsView.isActive()) return;
                if (showLoadingUI) mAchievementsView.setLoadingIndicator(false);

                mAchievementsView.showAchievements(category, achievements);
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mAchievementsView.isActive()) return;
                mAchievementsView.showLoadingError();
                mAchievementsView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void openAchievementDetails(@NonNull Achievement requestedAchievement) {
        checkNotNull(requestedAchievement, "requestedAchievement cannot be null!");
        this.mAchievementsView.showAchievementDetailsUi(requestedAchievement.getId());
    }
}
