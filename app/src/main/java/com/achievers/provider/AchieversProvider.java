package com.achievers.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.util.Log;

import com.achievers.provider.AchieversContract.Achievements;
import com.achievers.provider.AchieversContract.Categories;
import com.achievers.provider.AchieversDatabase.Tables;
import com.achievers.util.SelectionBuilder;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.achievers.util.LogUtils.LOGV;
import static com.achievers.util.LogUtils.makeLogTag;

public class AchieversProvider extends ContentProvider {

    private static final String TAG = makeLogTag(AchieversProvider.class);

    private AchieversDatabase mOpenHelper;

    private AchieversProviderUriMatcher mUriMatcher;

    @Override
    public boolean onCreate() {
        mOpenHelper = new AchieversDatabase(getContext());
        mUriMatcher = new AchieversProviderUriMatcher();
        
        return true;
    }

    private void deleteDatabase() {
        mOpenHelper.close();
        Context context = getContext();
        AchieversDatabase.deleteDatabase(context);
        mOpenHelper = new AchieversDatabase(getContext());
    }

    /** {@inheritDoc} */
    @Override
    public String getType(@NonNull Uri uri) {
        AchieversUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);
        
        return matchingUriEnum.contentType;
    }

    /**
     * Returns a tuple of question marks. For example, if {@code count} is 3, returns "(?,?,?)".
     */
    private String makeQuestionMarkTuple(int count) {
        if (count < 1) return "()";
        
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append("(?");
        for (int i = 1; i < count; i++) stringBuilder.append(",?");
        stringBuilder.append(")");
        
        return stringBuilder.toString();
    }

    /** {@inheritDoc} */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        AchieversUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);

        // Avoid the expensive string concatenation below if not loggable.
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            LOGV(TAG, "uri=" + uri + " code=" + matchingUriEnum.code + " proj=" +
                    Arrays.toString(projection) + " selection=" + selection + " args="
                    + Arrays.toString(selectionArgs) + ")");
        }

        // Most cases are handled with simple SelectionBuilder.
        final SelectionBuilder builder = buildExpandedSelection(uri, matchingUriEnum.code);

        boolean distinct = AchieversContractHelper.isQueryDistinct(uri);

        Cursor cursor = builder
                .where(selection, selectionArgs)
                .query(db, distinct, projection, sortOrder, null);

        Context context = getContext();
        if (null != context) {
            cursor.setNotificationUri(context.getContentResolver(), uri);
        }
        return cursor;
    }

    /** {@inheritDoc} */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        LOGV(TAG, "insert(uri=" + uri + ", values=" + values.toString() + ")");
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        AchieversUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);

        if (matchingUriEnum.table != null) {
            try {
                db.insertOrThrow(matchingUriEnum.table, null, values);
                notifyChange(uri);
            } catch (SQLiteConstraintException exception) {
                // Leaving this here as it's handy to to breakpoint on this throw when debugging a
                // bootstrap file issue.
                throw exception;
            }
        }

        switch (matchingUriEnum) {
            case CATEGORIES: {
                return Categories.buildCategoryUri(
                        values.getAsString(Categories.CATEGORY_ID));
            }
            case ACHIEVEMENTS: {
                return AchieversContract.Achievements.buildAchievementUri(
                        values.getAsString(Achievements.ACHIEVEMENT_ID));
            }
            case EVIDENCE: {
                return AchieversContract.Evidence.buildEvidenceUri(
                        values.getAsString(AchieversContract.Evidence.EVIDENCE_ID));
            }
            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        LOGV(TAG, "update(uri=" + uri + ", values=" + values.toString() + ")");

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSimpleSelection(uri);

        int retVal = builder.where(selection, selectionArgs).update(db, values);
        notifyChange(uri);

        return retVal;
    }

    /** {@inheritDoc} */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        LOGV(TAG, "delete(uri=" + uri + ")");

        if (uri == AchieversContract.BASE_CONTENT_URI) {
            // Handle whole database deletes (e.g. when signing out)
            deleteDatabase();
            notifyChange(uri);
            return 1;
        }

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSimpleSelection(uri);

        int retVal = builder.where(selection, selectionArgs).delete(db);
        notifyChange(uri);

        return retVal;
    }

    /**
     * Notifies the system that the given {@code uri} data has changed.
     * <p/>
     * We only notify changes if the uri wasn't called by the sync adapter, to avoid issuing a large
     * amount of notifications while doing a sync.
     */
    private void notifyChange(Uri uri) {
        if (!AchieversContractHelper.isUriCalledFromSyncAdapter(uri)) {
            Context context = getContext();
            context.getContentResolver().notifyChange(uri, null);
        }
    }

    /**
     * Apply the given set of {@link ContentProviderOperation}, executing inside
     * a {@link SQLiteDatabase} transaction. All changes will be rolled back if
     * any single one fails.
     */
    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Build a simple {@link SelectionBuilder} to match the requested
     * {@link Uri}. This is usually enough to support {@link #insert},
     * {@link #update}, and {@link #delete} operations.
     */
    private SelectionBuilder buildSimpleSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        AchieversUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);
        // The main Uris, corresponding to the root of each type of Uri, do not have any selection
        // criteria so the full table is used. The others apply a selection criteria.
        switch (matchingUriEnum) {
            case CATEGORIES:
            case ACHIEVEMENTS:
            case EVIDENCE:
                return builder.table(matchingUriEnum.table);
            case CATEGORIES_ID: {
                final String categoryId = Categories.getCategoryId(uri);
                return builder.table(Tables.CATEGORIES)
                        .where(Categories.CATEGORY_ID + "=?", categoryId);
            }
            case ACHIEVEMENTS_ID: {
                final String achievementId = Achievements.getAchievementId(uri);
                return builder.table(Tables.ACHIEVEMENTS)
                        .where(Achievements.ACHIEVEMENT_ID + "=?", achievementId);
            }
            case EVIDENCE_ID: {
                final String evidenceId = AchieversContract.Evidence.getEvidenceId(uri);
                return builder.table(Tables.EVIDENCE)
                        .where(AchieversContract.Evidence.EVIDENCE_ID + "=?", evidenceId);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri for " + uri);
            }
        }
    }

    /**
     * Build an advanced {@link SelectionBuilder} to match the requested
     * {@link Uri}. This is usually only used by {@link #query}, since it
     * performs table joins useful for {@link Cursor} data.
     */
    private SelectionBuilder buildExpandedSelection(Uri uri, int match) {
        final SelectionBuilder builder = new SelectionBuilder();
        AchieversUriEnum matchingUriEnum = mUriMatcher.matchCode(match);

        if (matchingUriEnum == null) throw new UnsupportedOperationException("Unknown uri: " + uri);

        switch (matchingUriEnum) {
            case CATEGORIES: {
                return builder.table(Tables.CATEGORIES);
            }
            case CATEGORIES_ID: {
                final String categoryId = AchieversContract.Categories.getCategoryId(uri);

                return builder.table(Tables.CATEGORIES)
                        .where(Categories.CATEGORY_ID + "=?", categoryId);
            }
            case ACHIEVEMENTS: {
                return builder.table(Tables.ACHIEVEMENTS);
            }
            case ACHIEVEMENTS_ID: {
                final String achievementId = AchieversContract.Achievements.getAchievementId(uri);

                return builder.table(Tables.ACHIEVEMENTS)
                        .where(Achievements.ACHIEVEMENT_ID + "=?", achievementId);
            }
            case EVIDENCE: {
                return builder.table(Tables.EVIDENCE);
            }
            case EVIDENCE_ID: {
                final String evidenceId = AchieversContract.Evidence.getEvidenceId(uri);

                return builder.table(Tables.EVIDENCE)
                        .where(AchieversContract.Evidence.EVIDENCE_ID + "=?", evidenceId);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        throw new UnsupportedOperationException("openFile is not supported for " + uri);
    }
}