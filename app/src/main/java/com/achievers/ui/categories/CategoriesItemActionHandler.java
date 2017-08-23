package com.achievers.ui.categories;

import com.achievers.entities.Category;

/**
 * Listens to user actions from the list item in ({@link CategoriesFragment}) and redirects them to the
 * Fragment's actions listener.
 */
public class CategoriesItemActionHandler {

    private CategoriesContract.Presenter mListener;

    public CategoriesItemActionHandler(CategoriesContract.Presenter listener) {
        mListener = listener;
    }

    /**
     * Called by the Data Binding library when the row is clicked.
     */
    public void categoryClicked(Category category) {
        mListener.openCategory(category);
    }
}