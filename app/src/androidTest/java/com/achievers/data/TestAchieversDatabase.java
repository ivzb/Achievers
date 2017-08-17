package com.achievers.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;

import static com.achievers.data.TestUtilities.getStaticIntegerField;
import static com.achievers.data.TestUtilities.getStaticStringField;
import static com.achievers.data.TestUtilities.readableClassNotFound;
import static com.achievers.data.TestUtilities.readableNoSuchField;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Used to test the database. Within these tests, we
 * test the following:
 * <p>
 * <p>
 * 1) Creation of the database with proper table(s)
 * 2) Insertion of single record into our tables
 * 3) When a record is already stored in the table with a particular id, a new record
 * with the same id will overwrite that record.
 * 4) Verify that NON NULL constraints are working properly on record inserts
 * 5) Verify auto increment is working with the ID
 * 6) Test the onUpgrade functionality of the AchieversDatabase
 */
@RunWith(AndroidJUnit4.class)
public class TestAchieversDatabase {

    private final Context context = InstrumentationRegistry.getTargetContext();

    private static final String packageName = "com.achievers";
    private static final String providerPackageName = packageName + ".provider";

    private Class achieversDatabaseClass;

    private Class categoriesClass;
    private Class achievementsClass;
    private Class evidenceClass;

    private static final String achieversContractName = ".AchieversContract";
    private static final String databaseName = ".AchieversDatabase";

    private static final String categoriesName = achieversContractName + "$Categories";
    private static final String achievementsName = achieversContractName + "$Achievements";
    private static final String evidenceName = achieversContractName + "$Evidence";

    private static final String databaseNameVariableName = "DATABASE_NAME";
    private static String REFLECTED_DATABASE_NAME;

    private static final String databaseVersionVariableName = "CURRENT_DATABASE_VERSION";
    private static int REFLECTED_DATABASE_VERSION;

    private static final String tableNameVariableName = "TABLE_NAME";

    private static String REFLECTED_CATEGORIES_TABLE_NAME;
    private static String REFLECTED_ACHIEVEMENTS_TABLE_NAME;
    private static String REFLECTED_EVIDENCE_TABLE_NAME;

    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;

    @Before
    public void before() {
        try {
            categoriesClass = Class.forName(providerPackageName + categoriesName);
            achievementsClass = Class.forName(providerPackageName + achievementsName);
            evidenceClass = Class.forName(providerPackageName + evidenceName);

            TestUtilities.checkIfClassImplementsBaseColumns(categoriesClass);
            TestUtilities.checkIfClassImplementsBaseColumns(achievementsClass);
            TestUtilities.checkIfClassImplementsBaseColumns(evidenceClass);

            REFLECTED_CATEGORIES_TABLE_NAME = getStaticStringField(categoriesClass, tableNameVariableName);
            REFLECTED_ACHIEVEMENTS_TABLE_NAME = getStaticStringField(achievementsClass, tableNameVariableName);
            REFLECTED_EVIDENCE_TABLE_NAME = getStaticStringField(evidenceClass, tableNameVariableName);

            achieversDatabaseClass = Class.forName(providerPackageName + databaseName);

            Class achieversDatabaseSuperclass = achieversDatabaseClass.getSuperclass();

            if (achieversDatabaseSuperclass == null || achieversDatabaseSuperclass.equals(Object.class)) {
                String noExplicitSuperclass =
                        "AchieversDatabase needs to extend SQLiteOpenHelper, but yours currently doesn't extend a class at all.";
                fail(noExplicitSuperclass);
            } else if (achieversDatabaseSuperclass != null) {
                String achieversDatabaseSuperclassName = achieversDatabaseSuperclass.getSimpleName();
                String doesNotExtendOpenHelper =
                        "AchieversDatabase needs to extend SQLiteOpenHelper but yours extends "
                                + achieversDatabaseSuperclassName;

                assertTrue(doesNotExtendOpenHelper,
                        SQLiteOpenHelper.class.isAssignableFrom(achieversDatabaseSuperclass));
            }

            REFLECTED_DATABASE_NAME = getStaticStringField(
                    achieversDatabaseClass, databaseNameVariableName);

            REFLECTED_DATABASE_VERSION = getStaticIntegerField(
                    achieversDatabaseClass, databaseVersionVariableName);

            Constructor achieversDatabaseCtor = achieversDatabaseClass.getConstructor(Context.class);

            dbHelper = (SQLiteOpenHelper) achieversDatabaseCtor.newInstance(context);

            context.deleteDatabase(REFLECTED_DATABASE_NAME);

            Method getWritableDatabase = SQLiteOpenHelper.class.getDeclaredMethod("getWritableDatabase");
            database = (SQLiteDatabase) getWritableDatabase.invoke(dbHelper);

        } catch (ClassNotFoundException e) {
            fail(readableClassNotFound(e));
        } catch (NoSuchFieldException e) {
            fail(readableNoSuchField(e));
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateDb() {
        final HashSet<String> tableNameHashSet = new HashSet<>();

        tableNameHashSet.add(REFLECTED_CATEGORIES_TABLE_NAME);
        tableNameHashSet.add(REFLECTED_ACHIEVEMENTS_TABLE_NAME);
        tableNameHashSet.add(REFLECTED_EVIDENCE_TABLE_NAME);

        String databaseIsNotOpen = "The database should be open and isn't";
        assertEquals(databaseIsNotOpen,
                true,
                database.isOpen());

        Cursor tableNameCursor = database.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table'",
                null);

        String errorInCreatingDatabase =
                "Error: This means that the database has not been created correctly";
        assertTrue(errorInCreatingDatabase,
                tableNameCursor.moveToFirst());

        do {
            tableNameHashSet.remove(tableNameCursor.getString(0));
        } while (tableNameCursor.moveToNext());

        assertTrue("Error: Your database was created without the expected tables.",
                tableNameHashSet.isEmpty());

        tableNameCursor.close();
    }

    @Test
    public void testInsertSingleRecordIntoCategoriesTable() {
        String tableName = REFLECTED_CATEGORIES_TABLE_NAME;
        ContentValues contentValues = TestUtilities.createTestCategoryContentValues();

        testInsertSingleRecordIntoTable(tableName, contentValues);
    }

    @Test
    public void testInsertSingleRecordIntoAchievementsTable() {
        String tableName = REFLECTED_ACHIEVEMENTS_TABLE_NAME;
        ContentValues contentValues = TestUtilities.createTestAchievementContentValues();

        testInsertSingleRecordIntoTable(tableName, contentValues);
    }

    @Test
    public void testInsertSingleRecordIntoEvidenceTable() {
        String tableName = REFLECTED_EVIDENCE_TABLE_NAME;
        ContentValues contentValues = TestUtilities.createTestEvidenceContentValues();

        testInsertSingleRecordIntoTable(tableName, contentValues);
    }

    private void testInsertSingleRecordIntoTable(String tableName, ContentValues contentValues) {
        long rowId = database.insert(
                tableName,
                null,
                contentValues);

        /* If the insert fails, database.insert returns -1 */
        int valueOfIdIfInsertFails = -1;
        String insertFailed = "Unable to insert into the database";
        assertNotSame(insertFailed,
                valueOfIdIfInsertFails,
                rowId);

        Cursor cursor = database.query(
                /* Name of table on which to perform the query */
                tableName,
                /* Columns; leaving this null returns every column in the table */
                null,
                /* Optional specification for columns in the "where" clause above */
                null,
                /* Values for "where" clause */
                null,
                /* Columns to group by */
                null,
                /* Columns to filter by row groups */
                null,
                /* Sort order to return in Cursor */
                null);

        String emptyQueryError = "Error: No Records returned from query";
        assertTrue(emptyQueryError,
                cursor.moveToFirst());

        String expectedResultDidntMatchActual =
                "Expected result values didn't match actual values.";
        TestUtilities.validateCurrentRecord(expectedResultDidntMatchActual,
                cursor,
                contentValues);

        /*
         * Since before every method annotated with the @Test annotation, the database is
         * deleted, we can assume in this method that there should only be one record in our
         * Weather table because we inserted it. If there is more than one record, an issue has
         * occurred.
         */
        assertFalse("Error: More than one record returned from weather query",
                cursor.moveToNext());

        cursor.close();
    }
}