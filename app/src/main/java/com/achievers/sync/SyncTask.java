package com.achievers.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.source.categories.CategoriesDataSource;
import com.achievers.data.source.categories.CategoriesRemoteDataSource;
import com.achievers.entities.Category;
import com.achievers.provider.AchieversContract;

import java.util.List;

public class SyncTask {

    /**
     * Performs the network request for updated categories, parses the JSON from that request, and
     * inserts the new categories into our ContentProvider.
     *
     * @param context Used to access utility methods and the ContentResolver
     */
    synchronized public static void syncCategories(final Context context) {
        CategoriesDataSource categoriesDataSource = CategoriesRemoteDataSource.getInstance();

        categoriesDataSource.loadCategories(new LoadCallback<Category>() {
            @Override
            public void onSuccess(List<Category> data) {
                ContentValues[] categoriesContentValues = new ContentValues[data.size()];

                for (int i = 0; i < data.size(); i++) {
                    Category currentCategory = data.get(i);
                    ContentValues categoryValues = new ContentValues();

                    categoryValues.put(AchieversContract.Categories.CATEGORY_ID, currentCategory.getId());
                    categoryValues.put(AchieversContract.Categories.CATEGORY_TITLE, currentCategory.getTitle());
                    categoryValues.put(AchieversContract.Categories.CATEGORY_DESCRIPTION, currentCategory.getDescription());
                    categoryValues.put(AchieversContract.Categories.CATEGORY_IMAGE_URL, currentCategory.getImageUrl());
                    categoryValues.put(AchieversContract.Categories.CATEGORY_PARENT_ID, currentCategory.getParentId());

                    categoriesContentValues[i] = categoryValues;
                }

                if (categoriesContentValues.length != 0) {
                    ContentResolver achieversContentResolver = context.getContentResolver();

                    achieversContentResolver.delete(
                            AchieversContract.Categories.CONTENT_URI,
                            null,
                            null);

                    achieversContentResolver.bulkInsert(
                            AchieversContract.Categories.CONTENT_URI,
                            categoriesContentValues);
                }
            }

            @Override
            public void onFailure(String message) {
                // todo: notify user and suggest to try again
            }
        });
    }
}