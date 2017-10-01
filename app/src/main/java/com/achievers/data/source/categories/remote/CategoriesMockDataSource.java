package com.achievers.data.source.categories.remote;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities.Category;
import com.achievers.data.source.categories.CategoriesDataSource;
import com.achievers.generator.CategoriesGenerator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CategoriesMockDataSource implements CategoriesDataSource {

    private static CategoriesDataSource sINSTANCE;

    private HashMap<Long, Category> mEntitiesById;
    private HashMap<Long, LinkedList<Category>> mEntitiesByParentId;

    public static CategoriesDataSource getInstance() {
        if (sINSTANCE == null) sINSTANCE = new CategoriesMockDataSource();

        return sINSTANCE;
    }

    // Prevent direct instantiation.
    private CategoriesMockDataSource() {
        mEntitiesById = new HashMap<>();
        mEntitiesByParentId = new HashMap<>();

        int[] sizes = new int[] { 5, 3, 2 };
        // todo: abstract it
        CategoriesGenerator generator = new CategoriesGenerator();
        List<Category> categories = generator.multiple(sizes);

        for (Category category: categories) {
            mEntitiesById.put(category.getId(), category);

            Long parentId = category.getParentId();
            if (!mEntitiesByParentId.containsKey(parentId)) {
                mEntitiesByParentId.put(parentId, new LinkedList<Category>());
            }

            mEntitiesByParentId.get(parentId).add(category);
        }
    }

    @Override
    public void get(int id, @NonNull GetCallback<Category> callback) {
        Category category = mEntitiesById.get(id);
        callback.onSuccess(category);
    }

    @Override
    public void load(Long parentId) {
        List<Category> categories = mEntitiesByParentId.get(parentId);
        //callback.onSuccess(categories);
    }

    @Override
    public void save(@NonNull List<Category> categories) {
        // saving categories to remote data source should not be possible
    }
}
