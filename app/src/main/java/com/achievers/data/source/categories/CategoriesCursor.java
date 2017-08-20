package com.achievers.data.source.categories;

import android.database.Cursor;

import com.achievers.entities.Category;

import static com.achievers.provider.AchieversContract.Categories.INDEXES.CATEGORY_DESCRIPTION;
import static com.achievers.provider.AchieversContract.Categories.INDEXES.CATEGORY_ID;
import static com.achievers.provider.AchieversContract.Categories.INDEXES.CATEGORY_IMAGE_URL;
import static com.achievers.provider.AchieversContract.Categories.INDEXES.CATEGORY_PARENT_ID;
import static com.achievers.provider.AchieversContract.Categories.INDEXES.CATEGORY_TITLE;

public class CategoriesCursor {

    public static Category from(Cursor cursor) {
        int id = cursor.getInt(CATEGORY_ID);
        String title = cursor.getString(CATEGORY_TITLE);
        String description = cursor.getString(CATEGORY_DESCRIPTION);
        String imageUrl = cursor.getString(CATEGORY_IMAGE_URL);
        Integer parentId = cursor.getInt(CATEGORY_PARENT_ID);

        return new Category(id, title, description, imageUrl, parentId);
    }
}
