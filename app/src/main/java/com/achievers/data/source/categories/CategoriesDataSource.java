package com.achievers.data.source.categories;

import android.support.annotation.NonNull;
import com.achievers.data.models.Category;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;

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
            final Integer parentId,
            @NonNull final LoadCallback<List<Category>> callback
    );

    void saveCategories(
            final Integer parentId,
            @NonNull final List<Category> categories,
            @NonNull final SaveCallback<Void> callback
    );

    void refreshCache();
}