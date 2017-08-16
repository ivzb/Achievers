package com.achievers.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.source.categories.CategoriesDataSource;
import com.achievers.data.source.categories.CategoriesRemoteDataSource;
import com.achievers.models.Category;
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
                    categoryValues.put(AchieversContract.Categories.CATEGORY_DESCRIPTION, currentCategory.getDescription());
                    categoryValues.put(AchieversContract.Categories.CATEGORY_IMAGE_URL, currentCategory.getImageUrl());
                    categoryValues.put(AchieversContract.Categories.CATEGORY_PARENT_ID, currentCategory.getParentId());

                    categoriesContentValues[i] = categoryValues;
                }

                /*
                 * In cases where our JSON contained an error code, getWeatherContentValuesFromJson
                 * would have returned null. We need to check for those cases here to prevent any
                 * NullPointerExceptions being thrown. We also have no reason to insert fresh data if
                 * there isn't any to insert.
                 */
                if (categoriesContentValues.length != 0) {
                    /* Get a handle on the ContentResolver to delete and insert data */
                    ContentResolver achieversContentResolver = context.getContentResolver();

                    /* Delete old data */
                    achieversContentResolver.delete(
                            AchieversContract.Categories.CONTENT_URI,
                            null,
                            null);

                    /* Insert our new weather data into Sunshine's ContentProvider */
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