package com.achievers;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.achievers.Categories.CategoriesAdapter;

public class AdapterSetters {
    @BindingAdapter("rvCategoriesAdapter")
    public static void setRvCategoriesAdapter(RecyclerView view, CategoriesAdapter adapter) {
        if (adapter != null) {
            view.setAdapter(adapter);
            view.setLayoutManager(new LinearLayoutManager(adapter.getContext()));
        }
    }
}
