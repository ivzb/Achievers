package com.achievers.ui.achievements;

import com.achievers.ui.categories.CategoriesContract;
import com.achievers.entities.Achievement;

/**
 * Listens to user actions from the list item in ({@link CategoriesContract.View}) and redirects them to the
 * Fragment's actions listener.
 */
public class AchievementsItemActionHandler {

    private AchievementsContract.Presenter mListener;

    public AchievementsItemActionHandler(AchievementsContract.Presenter listener) {
        this.mListener = listener;
    }

    /**
     * Called by the Data Binding library when the row is clicked.
     */
    public void achievementClicked(Achievement achievement) {
        this.mListener.openAchievementDetails(achievement);
    }
}