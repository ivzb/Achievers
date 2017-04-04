package com.achievers.Categories;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

import com.achievers.util.ScrollChildSwipeRefreshLayout;

public class SwipeRefreshLayoutDataBinding {

    /**
     * Reloads the data when the pull-to-refresh is triggered.
     * <p>
     * Creates the {@code android:onRefresh} for a {@link SwipeRefreshLayout}
     * that takes a {@link CategoriesContract.Presenter}.
     */
    @BindingAdapter("android:onRefresh") // todo: add Category parent id as parameter
    public static void setSwipeRefreshLayoutOnRefreshListener(ScrollChildSwipeRefreshLayout view, final CategoriesContract.Presenter presenter) {
        view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadCategories(null, true);
            }
        });
    }
}