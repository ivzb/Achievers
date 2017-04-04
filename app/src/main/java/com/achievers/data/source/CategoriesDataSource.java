package com.achievers.data.source;

import android.support.annotation.NonNull;
import com.achievers.data.Category;
import java.util.List;

/**
 * Main entry point for accessing Categories data.
 */
public interface CategoriesDataSource {
    interface GetCategoryCallback {
        void onLoaded(Category category);
        void onDataNotAvailable();
    }

    interface LoadCategoriesCallback {
        void onLoaded(List<Category> categories);
        void onDataNotAvailable();
    }

    void getCategory(@NonNull int categoryId, @NonNull GetCategoryCallback callback);
    void getCategories(Integer parentId, @NonNull LoadCategoriesCallback callback);
    void refreshCategories(List<Category> categories);
}