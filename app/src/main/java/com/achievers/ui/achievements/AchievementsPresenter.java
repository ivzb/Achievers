package com.achievers.ui.achievements;

import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.models.Achievement;
import com.achievers.models.Category;
import com.achievers.data.source.RESTClient;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.categories.CategoriesDataSource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class AchievementsPresenter implements AchievementsContract.Presenter {

    private final AchievementsDataSource mAchievementsDataSource;
    private final AchievementsContract.View mAchievementsView;
    private boolean mFirstLoad;
    private SparseBooleanArray mNoMoreData;
    private SparseIntArray mPages;

    public AchievementsPresenter(
            @NonNull AchievementsDataSource achievementsDataSource,
            @NonNull AchievementsContract.View achievementsView) {

        this.mAchievementsDataSource = checkNotNull(achievementsDataSource, "achievementsDataSource cannot be null");
        this.mAchievementsView = checkNotNull(achievementsView, "achievementsView cannot be null");
        this.mAchievementsView.setPresenter(this);
        this.mFirstLoad = true;
        this.mNoMoreData = new SparseBooleanArray();
        this.mPages = new SparseIntArray();
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

        if (this.mNoMoreData.get(category.getId(), false)) return; // no more data for this categoryId
        final int currentPage = this.mPages.get(category.getId(), 0);

        if (showLoadingUI) mAchievementsView.setLoadingIndicator(true);

        mAchievementsDataSource.loadAchievements(category.getId(), currentPage, new LoadCallback<Achievement>() {
            @Override
            public void onSuccess(final List<Achievement> achievements) {
                // The view may not be able to handle UI updates anymore
                if (!mAchievementsView.isActive()) return;
                if (showLoadingUI) mAchievementsView.setLoadingIndicator(false);

                mPages.put(category.getId(), currentPage + 1); // current category page++
                if (achievements.size() < RESTClient.getPageSize()) mNoMoreData.put(category.getId(), true); // no more data for this categoryId

                mAchievementsView.showAchievements(category, achievements);
            }

            @Override
            public void onFailure(String message) {
                // The view may not be able to handle UI updates anymore
                if (!mAchievementsView.isActive()) return;
                mAchievementsView.showLoadingError();
                if (showLoadingUI) mAchievementsView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void openAchievementDetails(@NonNull Achievement requestedAchievement) {
        checkNotNull(requestedAchievement, "requestedAchievement cannot be null!");
        this.mAchievementsView.showAchievementDetailsUi(requestedAchievement.getId());
    }
}
