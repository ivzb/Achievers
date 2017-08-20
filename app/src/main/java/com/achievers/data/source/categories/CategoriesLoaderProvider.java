package com.achievers.data.source.categories;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.achievers.provider.AchieversContract;

import static com.google.android.exoplayer2.util.Assertions.checkNotNull;

public class CategoriesLoaderProvider {

    @NonNull
    private final Context mContext;

    public CategoriesLoaderProvider(@NonNull Context context) {
        mContext = checkNotNull(context, "context cannot be null");
    }

    public Loader<Cursor> createCategoriesLoader() {
        return new CursorLoader(
                mContext,
                AchieversContract.Categories.buildCategoriesUri(),
                AchieversContract.Categories.COLUMNS,
                null, null, null);
    }
}
