package com.achievers;

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
import com.achievers.util.ScrollChildSwipeRefreshLayout;
import com.squareup.picasso.Picasso;

public class AdapterSetters {
    // Generic image loader
    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_cached_black_24dp)
                .into(view);
    }

    @BindingAdapter("android:src")
    public static void setImageUri(ImageView view, String imageUri) {
        if (imageUri == null) {
            view.setImageURI(null);
        } else {
            view.setImageURI(Uri.parse(imageUri));
        }
    }

    @BindingAdapter("android:src")
    public static void setImageUri(ImageView view, Uri imageUri) {
        view.setImageURI(imageUri);
    }

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

    // CategoriesFragment
    @BindingAdapter("rvAchievementsAdapter")
    public static void setRvAchievementsAdapter(RecyclerView view, AchievementsAdapter adapter) {
        if (adapter != null) {
            view.setAdapter(adapter);
            view.setLayoutManager(new LinearLayoutManager(adapter.getContext()));
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