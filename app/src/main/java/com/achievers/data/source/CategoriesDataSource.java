package com.achievers.data.source;

import android.support.annotation.NonNull;
import com.achievers.data.Category;
import com.achievers.data.source.callbacks.GetCallback;
import com.achievers.data.source.callbacks.LoadCallback;
import com.achievers.data.source.callbacks.SaveCallback;

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