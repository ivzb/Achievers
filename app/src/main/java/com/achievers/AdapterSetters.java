package com.achievers;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.achievers.AchievementDetail.AchievementDetailContract;
import com.achievers.Achievements.AchievementsAdapter;
import com.achievers.Categories.CategoriesAdapter;
import com.achievers.Categories.CategoriesContract;
import com.achievers.Evidence.EvidenceAdapter;
import com.achievers.data.Achievement;
import com.achievers.data.Category;
import com.achievers.util.EndlessRecyclerViewScrollListener;
import com.achievers.util.FreskoCircleProgressBarDrawable;
import com.achievers.util.ScrollChildSwipeRefreshLayout;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

public class AdapterSetters {
    // Fresco
    @BindingAdapter({ "bind:url", "bind:resources" })
    public static void loadImage(SimpleDraweeView view, String imageUrl, Resources resources) {
        Uri uri = Uri.parse(imageUrl);
        view.setImageURI(uri);

        GenericDraweeHierarchy hierarchy = view.getHierarchy();
        hierarchy.setFadeDuration(250);
        hierarchy.setPlaceholderImage(resources.getDrawable(R.drawable.bunny));
        hierarchy.setProgressBarImage(new FreskoCircleProgressBarDrawable());
    }

    // Binding Drawable to ImageView
    @BindingAdapter("android:src")
    public static void setImageDrawable(ImageView view, Drawable drawable) {
        view.setImageDrawable(drawable);
    }

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }

    // CategoriesFragment
    @BindingAdapter("rvCategoriesAdapter")
    public static void setRvCategoriesAdapter(RecyclerView view, CategoriesAdapter adapter) {
        if (adapter != null) {
            view.setAdapter(adapter);
            view.setLayoutManager(new LinearLayoutManager(adapter.getContext()));
        }
    }

    /**
     * Initializes RecyclerView adapter and loads automatically more items as
     * the user scrolls through the items (aka infinite scroll)
     * <p>
     * Creates the {@code android:onLoadMore} for a {@link EndlessRecyclerViewScrollListener}
     * that takes a {@link CategoriesContract.Presenter}.
     */
    // CategoriesFragment
    @BindingAdapter({ "adapter:onLoadMore", "adapter:rvAchievementsAdapter" })
    public static void setRvAchievementsAdapter(RecyclerView view, final CategoriesContract.Presenter presenter, final AchievementsAdapter adapter) {
        if (adapter != null) {
            view.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(adapter.getContext());
            view.setLayoutManager(linearLayoutManager);

            // Retain an instance so that you can call `resetState()` for fresh searches
            EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    // Triggered only when new data needs to be appended to the list
                    // Add whatever code is needed to append new items to the bottom of the list
                    Category category = adapter.getCategory();
                    presenter.loadAchievements(category.getId(), true);
                }
            };
            // Adds the scroll listener to RecyclerView
            view.addOnScrollListener(scrollListener);
        }
    }

    // AchievementDetailFragment
    @BindingAdapter("rvEvidenceAdapter")
    public static void setRvEvidenceAdapter(RecyclerView view, EvidenceAdapter adapter) {
        if (adapter != null) {
            view.setAdapter(adapter);
            view.setLayoutManager(new LinearLayoutManager(adapter.getContext()));
        }
    }

    /**
     * Reloads the data when the pull-to-refresh is triggered.
     * <p>
     * Creates the {@code android:onRefresh} for a {@link SwipeRefreshLayout}
     * that takes a {@link CategoriesContract.Presenter}.
     */
    @BindingAdapter({ "adapter:onRefresh", "adapter:category" })
    public static void setSwipeRefreshLayoutOnCategoryRefreshListener(ScrollChildSwipeRefreshLayout view, final CategoriesContract.Presenter presenter, final Category parent) {
        view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Integer parentId = parent != null ? parent.getId() : null;
                presenter.loadCategories(parentId, true);
            }
        });
    }

    /**
     * Reloads the data when the pull-to-refresh is triggered.
     * <p>
     * Creates the {@code android:onRefresh} for a {@link SwipeRefreshLayout}
     * that takes a {@link AchievementDetailContract.Presenter}.
     */
    @BindingAdapter({ "adapter:onRefresh", "adapter:achievement" })
    public static void setSwipeRefreshLayoutOnEvidenceRefreshListener(ScrollChildSwipeRefreshLayout view, final AchievementDetailContract.Presenter presenter, final Achievement achievement) {
        view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadEvidence(achievement.getId(), true);
            }
        });
    }
}