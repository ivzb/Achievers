package com.achievers.data.source.categories;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.entities.Category;

/**
 * Main entry point for accessing Categories data.
 */
public interface CategoriesDataSource {

    void getCategory(
            @NonNull final Integer categoryId,
            @NonNull final GetCallback<Category> callback
    );

    void loadCategories(
            @NonNull final LoadCallback<Category> callback
    );
}