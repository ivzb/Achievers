package com.achievers.data.sources.categories;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.achievers.provider.AchieversContract;

import static android.support.v4.util.Preconditions.checkNotNull;

public class CategoriesLoaderProvider {

    @NonNull
    private final Context mContext;

    public CategoriesLoaderProvider(@NonNull Context context) {
        mContext = checkNotNull(context, "context cannot be null");
    }

    public Loader<Cursor> createCategoriesLoader(String parentId) {
        return new CursorLoader(
                mContext,
                AchieversContract.Categories.buildCategoriesByParentUri(parentId),
                AchieversContract.Categories.COLUMNS,
                null, null, null);
    }
}