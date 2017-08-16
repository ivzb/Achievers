package com.achievers.data.source.categories;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.models.Category;

import java.util.List;

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