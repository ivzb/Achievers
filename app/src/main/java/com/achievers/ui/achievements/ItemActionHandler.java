package com.achievers.ui.achievements;

import com.achievers.ui.categories.CategoriesContract;
import com.achievers.data.entities.Achievement;

/**
 * Listens to user actions from the list item in ({@link CategoriesContract.View}) and redirects them to the
 * Fragment's actions listener.
 */
public class ItemActionHandler {

    private Contracts.Presenter mListener;

    public ItemActionHandler(Contracts.Presenter listener) {
        this.mListener = listener;
    }

    /**
     * Called by the Data Binding library when the row is clicked.
     */
    public void achievementClicked(Achievement achievement) {
//        this.mListener.openAchievementDetails(achievement);
    }
}