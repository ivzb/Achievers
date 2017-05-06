package com.achievers.Achievements;

import android.support.annotation.NonNull;

import com.achievers.data.Achievement;
import com.achievers.data.Category;
import com.achievers.data.source.AchievementsDataSource;
import com.achievers.data.source.CategoriesDataSource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Admin on 5/6/17.
 */

public class AchievementsPresenter {

    @Override
    public void loadAchievements(Integer categoryId, boolean forceUpdate) {
        // a network reload will be forced on first load.
        this.loadAchievements(categoryId, forceUpdate || this.mFirstLoad, true);
        this.mFirstLoad = false;
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link CategoriesDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadAchievements(final Integer categoryId, boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) mCategoriesView.setLoadingIndicator(true);
        if (forceUpdate) mAchievementsRepository.refreshCache();

        mAchievementsRepository.loadAchievements(categoryId, new AchievementsDataSource.LoadAchievementsCallback() {
            @Override
            public void onLoaded(final List<Achievement> achievements) {
                // The view may not be able to handle UI updates anymore
                if (!mCategoriesView.isActive()) return;
                if (showLoadingUI) mCategoriesView.setLoadingIndicator(false);

                mCategoriesRepository.getCategory(categoryId, new CategoriesDataSource.GetCategoryCallback() {
                    @Override
                    public void onLoaded(Category category) {
                        mCategoriesView.showAchievements(category, achievements);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mCategoriesView.showLoadingAchievementsError();
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mCategoriesView.isActive()) return;
                mCategoriesView.showLoadingAchievementsError();
                mCategoriesView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void openAchievementDetails(@NonNull Achievement requestedAchievement) {
        checkNotNull(requestedAchievement, "requestedAchievement cannot be null!");
        this.mCategoriesView.showAchievementDetailsUi(requestedAchievement.getId());
    }
}
