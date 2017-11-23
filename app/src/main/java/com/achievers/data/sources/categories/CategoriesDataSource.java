package com.achievers.data.sources.categories;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities.Category;

import java.util.List;

public interface CategoriesDataSource {

    void get(int id, @NonNull GetCallback<Category> callback);

    void load(Long parentId); //, @NonNull LoadCallback<Category> callback);

    void save(@NonNull List<Category> categories);
}