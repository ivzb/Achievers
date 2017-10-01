package com.achievers.ui.achievements;

import com.achievers.data.entities.Achievement;
import com.achievers.ui.categories.CategoriesContract;

/**
 * Listens to user actions from the list item in ({@link CategoriesContract.View}) and redirects them to the
 * Fragment's actions listener.
 */
public class ItemActionHandler {

    private Contracts.Presenter mPresenter;

    ItemActionHandler(Contracts.Presenter listener) {
        mPresenter = listener;
    }

    /**
     * Called by the Data Binding library when the row is clicked.
     */
    public void achievementClicked(Achievement achievement) {
//        this.mListener.openAchievementDetails(achievement);
    }
}