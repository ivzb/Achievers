package com.achievers.Achievements;

import com.achievers.Categories.CategoriesContract;
import com.achievers.data.Achievement;

/**
 * Listens to user actions from the list item in ({@link CategoriesContract.View}) and redirects them to the
 * Fragment's actions listener.
 */
public class AchievementsItemActionHandler {

    private CategoriesContract.Presenter mListener;

    public AchievementsItemActionHandler(CategoriesContract.Presenter listener) {
        this.mListener = listener;
    }

    /**
     * Called by the Data Binding library when the row is clicked.
     */
    public void achievementClicked(Achievement achievement) {
        this.mListener.openAchievementDetails(achievement);
    }
}