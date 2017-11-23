package com.achievers.data.sources.categories.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities.Category;
import com.achievers.data.sources.categories.CategoriesContentValues;
import com.achievers.data.sources.categories.CategoriesDataSource;
import com.achievers.provider.AchieversContract;

import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

public class CategoriesLocalDataSource implements CategoriesDataSource {

    private static CategoriesDataSource INSTANCE;

    private ContentResolver mContentResolver;

    private CategoriesLocalDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    public static CategoriesDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new CategoriesLocalDataSource(contentResolver);
        }

        return INSTANCE;
    }

    @Override
    public void get(int id, @NonNull GetCallback<Category> callback) {
        // no-op since the data is loader via Cursor Loader
    }

    @Override
    public void load(Long parentId) {
        // no-op since the data is loaded via Cursor Loader
    }

    @Override
    public void save(@NonNull List<Category> categories) {
        checkNotNull(categories);

        Uri uri = AchieversContract.Categories.buildCategoriesUri();
        ContentValues[] values = CategoriesContentValues.from(categories);

        mContentResolver.bulkInsert(uri, values);
    }
}
