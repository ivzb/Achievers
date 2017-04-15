package com.achievers;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.achievers.Achievements.AchievementsAdapter;
import com.achievers.Categories.CategoriesAdapter;
import com.squareup.picasso.Picasso;

public class AdapterSetters {
    // Generic image loader
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_cached_black_24dp)
                .into(view);
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
}
