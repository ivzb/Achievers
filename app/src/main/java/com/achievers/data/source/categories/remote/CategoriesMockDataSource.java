package com.achievers.data.source.categories.remote;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.source.categories.CategoriesDataSource;
import com.achievers.entities.Category;
import com.achievers.seed.CategoriesSeed;
import com.achievers.seed.Seed;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CategoriesMockDataSource implements CategoriesDataSource {

    private static CategoriesDataSource INSTANCE;
//    private HashMap<Integer, List<Category>> mEntities;
    private HashMap<Integer, LinkedList<Category>> mEntitiesById;
    private HashMap<Integer, LinkedList<Category>> mEntitiesByParentId;

    public static CategoriesDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new CategoriesMockDataSource();

        return INSTANCE;
    }

    // Prevent direct instantiation.
    private CategoriesMockDataSource() {
//        Seed<Category> seed = new CategoriesSeed();
//        mEntities = seed.getData();
    }

    @Override
    public void get(int id, @NonNull GetCallback<Category> callback) {

    }

    @Override
    public void load(Integer parentId, @NonNull LoadCallback<Category> callback) {

    }

    @Override
    public void save(@NonNull List<Category> categories) {

    }
}
