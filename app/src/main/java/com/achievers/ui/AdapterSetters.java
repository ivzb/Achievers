package com.achievers.ui;

import android.content.res.Resources;
import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.achievers.R;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Category;
import com.achievers.ui.achievement.AchievementDetailContract;
import com.achievers.ui.achievements.Adapter;
import com.achievers.ui.achievements.Contracts;
import com.achievers.ui.add_achievement.Adapters.InvolvementRecyclerViewAdapter;
import com.achievers.ui.categories.CategoriesAdapter;
import com.achievers.ui.categories.CategoriesContract;
import com.achievers.ui.evidence.EvidenceAdapter;
import com.achievers.utils.EndlessRecyclerViewScrollListener;
import com.achievers.utils.FreskoCircleProgressBarDrawable;
import com.achievers.utils.ScrollChildSwipeRefreshLayout;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

import im.ene.toro.Toro;

public class AdapterSetters {
    // Fresco
    @BindingAdapter({ "bind:url", "bind:resources" })
    public static void loadImage(SimpleDraweeView view, String imageUrl, Resources resources) {
        if (imageUrl != null) {
            Uri uri = Uri.parse(imageUrl);
            view.setImageURI(uri);

            GenericDraweeHierarchy hierarchy = view.getHierarchy();
            hierarchy.setFadeDuration(250);

            Drawable bunny = ResourcesCompat.getDrawable(resources, R.drawable.bunny, null);
            hierarchy.setPlaceholderImage(bunny);
            hierarchy.setProgressBarImage(new FreskoCircleProgressBarDrawable());
        }
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
    @BindingAdapter("categoriesAdapter")
//    @BindingAdapter({ "bind:adapter", "bind:cursor" })
    public static void setRvCategoriesAdapter(RecyclerView view, CategoriesAdapter adapter/*, Cursor cursor*/) {
        if (adapter != null) {
            view.setAdapter(adapter);
            view.setLayoutManager(new LinearLayoutManager(adapter.getContext()));
//            adapter.swapCursor(cursor);
        }
    }

    @BindingAdapter("categoriesCursor")
//    @BindingAdapter({ "bind:adapter", "bind:cursor" })
    public static void setCategoriesCursor(RecyclerView view, Cursor cursor) {
        if (view.getAdapter() != null) {
            CategoriesAdapter adapter = (CategoriesAdapter) view.getAdapter();
            adapter.swapCursor(cursor);
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
    public static void setRvAchievementsAdapter(RecyclerView view, final Contracts.Presenter presenter, final Adapter adapter) {
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
                    presenter.loadAchievements(page/*category*/);
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
            view.setHasFixedSize(false);
            view.setAdapter(adapter);
            view.setLayoutManager(new LinearLayoutManager(adapter.getContext()));

            Toro.register(view);
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
                //Integer parentId = parent != null ? parent.getId() : null;
                // todo: refresh accurate parent
                presenter.loadCategories(null);
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

    // AddAchievementFragment
    @BindingAdapter("rvInvolvementAdapter")
    public static void setRvInvolvementAdapter(RecyclerView recyclerView, InvolvementRecyclerViewAdapter adapter) {
        if (adapter != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(adapter.getContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
        }
    }
}