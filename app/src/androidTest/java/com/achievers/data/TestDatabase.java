package com.achievers.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.achievers.provider.AchieversContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;

import static com.achievers.data.TestUtilities.ID_TO_INSERT;
import static com.achievers.data.TestUtilities.getConstantNameByStringValue;
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
public class TestDatabase {

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
        ContentValues contentValues = TestUtilities.createTestCategoryContentValues(ID_TO_INSERT, false);

        testInsertSingleRecordIntoTable(tableName, contentValues);
    }

    @Test
    public void testInsertSingleRecordIntoAchievementsTable() {
        String tableName = REFLECTED_ACHIEVEMENTS_TABLE_NAME;
        ContentValues contentValues = TestUtilities.createTestAchievementContentValues(ID_TO_INSERT);

        testInsertSingleRecordIntoTable(tableName, contentValues);
    }

    @Test
    public void testInsertSingleRecordIntoEvidenceTable() {
        String tableName = REFLECTED_EVIDENCE_TABLE_NAME;
        ContentValues contentValues = TestUtilities.createTestEvidenceContentValues(ID_TO_INSERT);

        testInsertSingleRecordIntoTable(tableName, contentValues);
    }

    private void testInsertSingleRecordIntoTable(String tableName, ContentValues contentValues) {
        long rowId = database.insert(
                tableName,
                null,
                contentValues);

        int valueOfIdIfInsertFails = -1;
        String insertFailed = "Unable to insert into the database";
        assertNotSame(insertFailed,
                valueOfIdIfInsertFails,
                rowId);

        Cursor cursor = database.query(
                tableName,
                null, null, null, null, null, null);

        String emptyQueryError = "Error: No Records returned from query";
        assertTrue(emptyQueryError,
                cursor.moveToFirst());

        String expectedResultDidntMatchActual =
                "Expected result values didn't match actual values.";
        TestUtilities.validateCurrentRecord(expectedResultDidntMatchActual,
                cursor,
                contentValues);

        assertFalse("Error: More than one record returned from query",
                cursor.moveToNext());

        cursor.close();
    }

    @Test
    public void testOnUpgradeBehavesCorrectly() {
        testInsertSingleRecordIntoCategoriesTable();
        testInsertSingleRecordIntoAchievementsTable();
        testInsertSingleRecordIntoEvidenceTable();

        dbHelper.onUpgrade(database, 13, 14);

        testOnUpgrade(REFLECTED_CATEGORIES_TABLE_NAME);
        testOnUpgrade(REFLECTED_ACHIEVEMENTS_TABLE_NAME);
        testOnUpgrade(REFLECTED_EVIDENCE_TABLE_NAME);

        database.close();
    }

    private void testOnUpgrade(String tableName) {
        Cursor tableNameCursor = database.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'",
                null);

        int expectedTableCount = 1;
        String shouldHaveSingleTable = "There should only be one table returned from this query.";
        assertEquals(shouldHaveSingleTable,
                expectedTableCount,
                tableNameCursor.getCount());

        tableNameCursor.close();

        Cursor shouldBeEmptyCursor = database.query(
                tableName,
                null, null, null, null, null, null);

        int expectedRecordCountAfterUpgrade = 0;

        String tableShouldBeEmpty =
                tableName + " table should be empty after upgrade, but wasn't."
                        + "\nNumber of records: ";
        assertEquals(tableShouldBeEmpty,
                expectedRecordCountAfterUpgrade,
                shouldBeEmptyCursor.getCount());
    }

    @Test
    public void testIntegerAutoincrementCategories() {
        testInsertSingleRecordIntoCategoriesTable();

        String tableName = REFLECTED_CATEGORIES_TABLE_NAME;
        String column = AchieversContract.Categories.CATEGORY_ID;
        ContentValues contentValues = TestUtilities.createTestCategoryContentValues(ID_TO_INSERT, false);

        testIntegerAutoincrement(tableName, column, contentValues);
    }

    @Test
    public void testIntegerAutoincrementAchievements() {
        testInsertSingleRecordIntoAchievementsTable();

        String tableName = REFLECTED_ACHIEVEMENTS_TABLE_NAME;
        String column = AchieversContract.Achievements.ACHIEVEMENT_ID;
        ContentValues contentValues = TestUtilities.createTestAchievementContentValues(ID_TO_INSERT);

        testIntegerAutoincrement(tableName, column, contentValues);
    }

    @Test
    public void testIntegerAutoincrementEvidence() {
        testInsertSingleRecordIntoEvidenceTable();

        String tableName = REFLECTED_EVIDENCE_TABLE_NAME;
        String column = AchieversContract.Evidence.EVIDENCE_ID;
        ContentValues contentValues = TestUtilities.createTestEvidenceContentValues(ID_TO_INSERT);

        testIntegerAutoincrement(tableName, column, contentValues);
    }

    private void testIntegerAutoincrement(String tableName, String columnId, ContentValues contentValues) {

        int originalId = contentValues.getAsInteger(columnId);

        long firstRowId = database.insert(
                tableName,
                null,
                contentValues);

        database.delete(
                tableName,
                AchieversContract.Categories._ID + " == " + firstRowId,
                null);

        int nextId = originalId + 1;
        contentValues.put(columnId, nextId);

        long secondRowId = database.insert(
                tableName,
                null,
                contentValues);

        String sequentialInsertsDoNotAutoIncrementId =
                "IDs were reused and shouldn't be if autoincrement is setup properly.";
        assertNotSame(sequentialInsertsDoNotAutoIncrementId,
                firstRowId, secondRowId);
    }

    @Test
    public void testNullColumnConstraintsCategory() {
        String tableName = REFLECTED_CATEGORIES_TABLE_NAME;

        ContentValues contentValues = TestUtilities.createTestCategoryContentValues(ID_TO_INSERT, false);
        Class contractClass = AchieversContract.Categories.class;

        testNullColumnConstraints(tableName, contentValues, contractClass);
    }

    @Test
    public void testNullColumnConstraintsAchievement() {
        String tableName = REFLECTED_ACHIEVEMENTS_TABLE_NAME;

        ContentValues contentValues = TestUtilities.createTestAchievementContentValues(ID_TO_INSERT);
        Class contractClass = AchieversContract.Achievements.class;

        testNullColumnConstraints(tableName, contentValues, contractClass);
    }

    @Test
    public void testNullColumnConstraintsEvidence() {
        String tableName = REFLECTED_EVIDENCE_TABLE_NAME;

        ContentValues contentValues = TestUtilities.createTestEvidenceContentValues(ID_TO_INSERT);
        Class contractClass = AchieversContract.Evidence.class;

        testNullColumnConstraints(tableName, contentValues, contractClass);
    }

    private void testNullColumnConstraints(String tableName, ContentValues contentValues, Class contractClass) {
        Cursor tableCursor = database.query(
                tableName,
                null, null, null, null, null, null);

        String[] tableColumnNames = tableCursor.getColumnNames();
        tableCursor.close();

        ContentValues testValuesReferenceCopy = new ContentValues(contentValues);

        for (String columnName : tableColumnNames) {

            if (columnName.equals(BaseColumns._ID)) continue;

            contentValues.putNull(columnName);

            long shouldFailRowId = database.insert(
                    tableName,
                    null,
                    contentValues);

            String variableName = getConstantNameByStringValue(
                    contractClass,
                    columnName);

            String nullRowInsertShouldFail =
                    "Insert should have failed due to a null value for column: '" + columnName + "'"
                            + ", but didn't."
                            + "\n Check that you've added NOT NULL to " + variableName
                            + " in your create table statement in the Contract class."
                            + "\n Row ID: ";

            assertEquals(nullRowInsertShouldFail,
                    -1,
                    shouldFailRowId);

            contentValues.put(columnName, testValuesReferenceCopy.getAsDouble(columnName));
        }

        dbHelper.close();
    }

    @Test
    public void testDuplicateCategoryIdInsertBehaviorShouldReplace() {
        String tableName = REFLECTED_CATEGORIES_TABLE_NAME;
        ContentValues contentValues = TestUtilities.createTestCategoryContentValues(ID_TO_INSERT, false);
        String columnId = AchieversContract.Categories.CATEGORY_ID;

        testDuplicateIdInsertBehaviorShouldReplace(tableName, contentValues, columnId);
    }

    @Test
    public void testDuplicateAchievementIdInsertBehaviorShouldReplace() {
        String tableName = REFLECTED_ACHIEVEMENTS_TABLE_NAME;
        ContentValues contentValues = TestUtilities.createTestAchievementContentValues(ID_TO_INSERT);
        String columnId = AchieversContract.Achievements.ACHIEVEMENT_ID;

        testDuplicateIdInsertBehaviorShouldReplace(tableName, contentValues, columnId);
    }

    @Test
    public void testDuplicateEvidenceIdInsertBehaviorShouldReplace() {
        String tableName = REFLECTED_EVIDENCE_TABLE_NAME;
        ContentValues contentValues = TestUtilities.createTestEvidenceContentValues(ID_TO_INSERT);
        String columnId = AchieversContract.Evidence.EVIDENCE_ID;

        testDuplicateIdInsertBehaviorShouldReplace(tableName, contentValues, columnId);
    }

    private void testDuplicateIdInsertBehaviorShouldReplace(String tableName, ContentValues contentValues, String columnId) {

        for (int i = 0; i < 2; i++) {
            database.insert(
                    tableName,
                    null,
                    contentValues);
        }

        Cursor newIdCursor = database.query(
                tableName,
                new String[] { columnId },
                null, null, null, null, null);

        String recordWithNewIdNotFound =
                "New record did not overwrite the previous record for the same id.";
        assertTrue(recordWithNewIdNotFound,
                newIdCursor.getCount() == 1);

        newIdCursor.close();
    }
}