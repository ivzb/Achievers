package com.achievers.data.source.categories;

import android.content.ContentValues;

import com.achievers.data.entities.Category;

import java.util.List;

import static com.achievers.provider.AchieversContract.CategoriesColumns.*;

public class CategoriesContentValues {

    public static ContentValues from(Category category) {
        ContentValues values = new ContentValues();

        values.put(CATEGORY_ID, category.getId());
        values.put(CATEGORY_TITLE, category.getTitle());
        values.put(CATEGORY_DESCRIPTION, category.getDescription());
        values.put(CATEGORY_IMAGE_URL, category.getImageUrl());
        values.put(CATEGORY_PARENT_ID, category.getParentId());

        return values;
    }

    public static ContentValues[] from(List<Category> categories) {
        ContentValues[] values = new ContentValues[categories.size()];

        for (int i = 0; i < categories.size(); i++) {
            values[i] = from(categories.get(i));
        }

        return values;
    }
}