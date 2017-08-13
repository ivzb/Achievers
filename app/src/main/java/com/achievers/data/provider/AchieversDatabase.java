package com.achievers.data.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.achievers.data.provider.AchieversContract.Achievements;
import com.achievers.data.provider.AchieversContract.AchievementsColumns;
import com.achievers.data.provider.AchieversContract.Categories;
import com.achievers.data.provider.AchieversContract.CategoriesColumns;
import com.achievers.data.provider.AchieversContract.EvidenceColumns;

import static com.achievers.util.LogUtils.makeLogTag;

/**
 * Helper for managing {@link SQLiteDatabase} that stores data for
 * {@link AchieversProvider}.
 */
public class AchieversDatabase extends SQLiteOpenHelper {
    private static final String TAG = makeLogTag(AchieversDatabase.class);

    private static final String DATABASE_NAME = "achievers.db";

    private static final int VER_2017_ALPHA = 1;

    private static final int CURRENT_DATABASE_VERSION = VER_2017_ALPHA;

    interface Tables {
        String CATEGORIES = "categories";
        String ACHIEVEMENTS = "achievements";
        String EVIDENCE = "evidence";

        // todo joins
    }

    /** {@code REFERENCES} clauses. */
    private interface References {
        String CATEGORY_ID = "REFERENCES " + Tables.CATEGORIES + "(" + Categories.CATEGORY_ID + ")";
        String ACHIEVEMENT_ID = "REFERENCES " + Tables.ACHIEVEMENTS + "(" + Achievements.ACHIEVEMENT_ID + ")";
    }

    public AchieversDatabase(Context context) {
        super(context, DATABASE_NAME, null, CURRENT_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.CATEGORIES + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CategoriesColumns.CATEGORY_ID + " INTEGER NOT NULL INDEX,"
                + CategoriesColumns.CATEGORY_DESCRIPTION + " TEXT NOT NULL,"
                + CategoriesColumns.CATEGORY_IMAGE_URL + " TEXT NOT NULL,"
                + CategoriesColumns.CATEGORY_PARENT_ID + " INTEGER INDEX " + References.CATEGORY_ID + ","
                + "UNIQUE (" + CategoriesColumns.CATEGORY_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE " + Tables.ACHIEVEMENTS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AchievementsColumns.ACHIEVEMENT_ID + " INTEGER NOT NULL INDEX,"
                + AchievementsColumns.ACHIEVEMENT_TITLE + " TEXT NOT NULL,"
                + AchievementsColumns.ACHIEVEMENT_DESCRIPTION + " TEXT NOT NULL,"
                + AchievementsColumns.ACHIEVEMENT_IMAGE_URL + " TEXT NOT NULL,"
                + AchievementsColumns.ACHIEVEMENT_CATEGORY_ID + " INTEGER NOT NULL INDEX " + References.CATEGORY_ID + ","
                + AchievementsColumns.ACHIEVEMENT_INVOLVEMENT + " INTEGER NOT NULL,"
                + AchievementsColumns.ACHIEVEMENT_AUTHOR_ID + " INTEGER NOT NULL,"
                + "UNIQUE (" + AchievementsColumns.ACHIEVEMENT_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE " + Tables.EVIDENCE + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EvidenceColumns.EVIDENCE_ID + " INTEGER NOT NULL INDEX,"
                + EvidenceColumns.EVIDENCE_TITLE + " TEXT NOT NULL,"
                + EvidenceColumns.EVIDENCE_TYPE + " INTEGER NOT NULL,"
                + EvidenceColumns.EVIDENCE_URL + " TEXT NOT NULL,"
                + EvidenceColumns.EVIDENCE_ACHIEVEMENT_ID + " INTEGER NOT NULL INDEX " + References.ACHIEVEMENT_ID + ","
                + EvidenceColumns.EVIDENCE_AUTHOR_ID + " INTEGER NOT NULL,"
                + "UNIQUE (" + EvidenceColumns.EVIDENCE_ID + ") ON CONFLICT REPLACE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.ACHIEVEMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.EVIDENCE);

        onCreate(db);
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}