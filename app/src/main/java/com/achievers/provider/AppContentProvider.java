package com.achievers.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.achievers.data.source.categories.CategoryDao;
import com.achievers.entities.Category;

import java.util.ArrayList;

public class AppContentProvider extends ContentProvider {

    /** The authority of this content provider. */
    public static final String AUTHORITY = "com.achievers.provider";

    /** The URI for the Cheese table. */
    public static final Uri URI_CATEGORY = Uri.parse(
            "content://" + AUTHORITY + "/" + Category.TABLE_NAME);

    /** The match code for some items in the Cheese table. */
    private static final int CODE_CATEGORIES = 1;

    /** The match code for an item in the Cheese table. */
    private static final int CODE_CATEGORY = 2;

    /** The URI matcher. */
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, Category.TABLE_NAME, CODE_CATEGORIES);
        MATCHER.addURI(AUTHORITY, Category.TABLE_NAME + "/*", CODE_CATEGORY);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_CATEGORIES || code == CODE_CATEGORY) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            CategoryDao categories = AppDatabase.getInstance(context).categories();
            final Cursor cursor;
            if (code == CODE_CATEGORIES) {
                cursor = categories.selectAll();
            } else {
                cursor = categories.selectById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_CATEGORIES:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + Category.TABLE_NAME;
            case CODE_CATEGORY:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + Category.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (MATCHER.match(uri)) {
            case CODE_CATEGORIES:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = AppDatabase.getInstance(context).categories()
                        .insert(Category.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_CATEGORY:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_CATEGORIES:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_CATEGORY:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = AppDatabase.getInstance(context).categories()
                        .deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_CATEGORIES:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_CATEGORY:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final Category category = Category.fromContentValues(values);
                category.setId(ContentUris.parseId(uri));
                final int count = AppDatabase.getInstance(context).categories()
                        .update(category);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(
            @NonNull ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final Context context = getContext();
        if (context == null) {
            return new ContentProviderResult[0];
        }
        final AppDatabase database = AppDatabase.getInstance(context);
        database.beginTransaction();
        try {
            final ContentProviderResult[] result = super.applyBatch(operations);
            database.setTransactionSuccessful();
            return result;
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] valuesArray) {
        switch (MATCHER.match(uri)) {
            case CODE_CATEGORIES:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final AppDatabase database = AppDatabase.getInstance(context);
                final Category[] categories = new Category[valuesArray.length];
                for (int i = 0; i < valuesArray.length; i++) {
                    categories[i] = Category.fromContentValues(valuesArray[i]);
                }
                return database.categories().insertAll(categories).length;
            case CODE_CATEGORY:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

}