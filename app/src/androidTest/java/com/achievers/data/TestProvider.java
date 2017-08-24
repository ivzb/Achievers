package com.achievers.data;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.achievers.provider.AchieversContract;
import com.achievers.provider.AchieversDatabase;
import com.achievers.provider.AchieversProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.achievers.data.TestUtilities.BULK_INSERT_RECORDS_TO_INSERT;
import static com.achievers.data.TestUtilities.ID_TO_INSERT;
import static com.achievers.data.TestUtilities.createBulkInsertTestAchievementValues;
import static com.achievers.data.TestUtilities.createBulkInsertTestCategoryValues;
import static com.achievers.data.TestUtilities.createBulkInsertTestEvidenceValues;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class TestProvider {

    /* Context used to access various parts of the system */
    private final Context mContext = InstrumentationRegistry.getTargetContext();

    /**
     * Because we annotate this method with the @Before annotation, this method will be called
     * before every single method with an @Test annotation. We want to start each test clean, so we
     * delete all entries in the weather table to do so.
     */
    @Before
    public void setUp() {
        deleteAllRecordsFromDatabase();
    }

    /**
     * This test checks to make sure that the content provider is registered correctly in the
     * AndroidManifest file. If it fails, you should check the AndroidManifest to see if you've
     * added a <provider/> tag and that you've properly specified the android:authorities attribute.
     * <p>
     * Potential causes for failure:
     * <p>
     *   1) AchieversProvider was registered with the incorrect authority
     * <p>
     *   2) AchieversProvider was not registered at all
     */
    @Test
    public void testProviderRegistry() {
        String packageName = mContext.getPackageName();
        String achieversProviderClassName = AchieversProvider.class.getName();
        ComponentName componentName = new ComponentName(packageName, achieversProviderClassName);

        try {
            PackageManager pm = mContext.getPackageManager();

            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
            String actualAuthority = providerInfo.authority;
            String expectedAuthority = AchieversContract.CONTENT_AUTHORITY;

            /* Make sure that the registered authority matches the authority from the Contract */
            String incorrectAuthority =
                    "Error: AchieversProvider registered with authority: " + actualAuthority +
                            " instead of expected authority: " + expectedAuthority;
            assertEquals(incorrectAuthority,
                    actualAuthority,
                    expectedAuthority);

        } catch (PackageManager.NameNotFoundException e) {
            String providerNotRegisteredAtAll =
                    "Error: AchieversProvider not registered at " + mContext.getPackageName();
            /*
             * This exception is thrown if the ContentProvider hasn't been registered with the
             * manifest at all. If this is the case, you need to double check your
             * AndroidManifest file
             */
            fail(providerNotRegisteredAtAll);
        }
    }

    @Test
    public void testBasicCategoryQuery() {
        String tableName = AchieversContract.Categories.TABLE_NAME;
        ContentValues contentValues = TestUtilities.createTestCategoryContentValues(ID_TO_INSERT, false);
        Uri contentUri = AchieversContract.Categories.buildCategoriesUri();

        testBasicQuery(contentValues, tableName, contentUri);
    }

    @Test
    public void testBasicAchievementsQuery() {
        String tableName = AchieversContract.Achievements.TABLE_NAME;
        ContentValues contentValues = TestUtilities.createTestAchievementContentValues(ID_TO_INSERT);
        Uri contentUri = AchieversContract.Achievements.buildAchievementsUri();

        testBasicQuery(contentValues, tableName, contentUri);
    }

    @Test
    public void testBasicEvidenceQuery() {
        String tableName = AchieversContract.Evidence.TABLE_NAME;
        ContentValues contentValues = TestUtilities.createTestEvidenceContentValues(ID_TO_INSERT);
        Uri contentUri = AchieversContract.Evidence.buildEvidenceUri();

        testBasicQuery(contentValues, tableName, contentUri);
    }

    private void testBasicQuery(ContentValues contentValues, String tableName, Uri contentUri) {
        AchieversDatabase dbHelper = new AchieversDatabase(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        long rowId = database.insert(
                tableName,
                null,
                contentValues);

        String insertFailed = "Unable to insert into the database";
        assertTrue(insertFailed, rowId != -1);

        database.close();

        Cursor cursor = mContext.getContentResolver().query(
                contentUri,
                null, null, null, null);

        /* This method will ensure that we  */
        TestUtilities.validateThenCloseCursor("testBasicQuery",
                cursor,
                contentValues);
    }

    @Test
    public void testCategoryParentQuery() {
        String tableName = AchieversContract.Categories.TABLE_NAME;
        ContentValues contentValues = TestUtilities.createTestCategoryContentValues(ID_TO_INSERT, true);

        AchieversDatabase dbHelper = new AchieversDatabase(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        long rowId = database.insert(
                tableName,
                null,
                contentValues);

        String insertFailed = "Unable to insert into the database";
        assertTrue(insertFailed, rowId != -1);

        database.close();

        Cursor cursor = mContext.getContentResolver().query(
                AchieversContract.Categories.buildCategoriesByParentUri(String.valueOf(contentValues.get(AchieversContract.Categories.CATEGORY_ID))),
                null, null, null, null);

        /* This method will ensure that we  */
        TestUtilities.validateThenCloseCursor("testBasicQuery",
                cursor,
                contentValues);
    }

    @Test
    public void testBulkInsertCategories() {
        ContentValues[] bulkInsertContentValues = createBulkInsertTestCategoryValues();
        Uri insertUri = AchieversContract.Categories.buildCategoriesUri();
        String sortColumn = AchieversContract.Categories.CATEGORY_ID;
        testBulkInsert(bulkInsertContentValues, insertUri, sortColumn);
    }

    @Test
    public void testBulkInsertAchievements() {
        ContentValues[] bulkInsertContentValues = createBulkInsertTestAchievementValues();
        Uri insertUri = AchieversContract.Achievements.buildAchievementsUri();
        String sortColumn = AchieversContract.Achievements.ACHIEVEMENT_ID;
        testBulkInsert(bulkInsertContentValues, insertUri, sortColumn);
    }

    @Test
    public void testBulkInsertEvidence() {
        ContentValues[] bulkInsertContentValues = createBulkInsertTestEvidenceValues();
        Uri insertUri = AchieversContract.Evidence.buildEvidenceUri();
        String sortColumn = AchieversContract.Evidence.EVIDENCE_ID;
        testBulkInsert(bulkInsertContentValues, insertUri, sortColumn);
    }

    private void testBulkInsert(ContentValues[] bulkInsertContentValues, Uri insertUri, String sortColumn) {
        TestUtilities.TestContentObserver observer = TestUtilities.getTestContentObserver();

        ContentResolver contentResolver = mContext.getContentResolver();

        contentResolver.registerContentObserver(
                insertUri,
                true,
                observer);

        int insertCount = contentResolver.bulkInsert(
                insertUri,
                bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        contentResolver.unregisterContentObserver(observer);

        String expectedAndActualInsertedRecordCountDoNotMatch =
                "Number of expected records inserted does not match actual inserted record count";
        assertEquals(expectedAndActualInsertedRecordCountDoNotMatch,
                insertCount,
                BULK_INSERT_RECORDS_TO_INSERT);

        Cursor cursor = mContext.getContentResolver().query(
                insertUri,
                null, null, null,
                sortColumn + " ASC");

        assertEquals(cursor.getCount(), BULK_INSERT_RECORDS_TO_INSERT);

        cursor.moveToFirst();
        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++, cursor.moveToNext()) {
            TestUtilities.validateCurrentRecord(
                    "testBulkInsert. Error validating entry " + i,
                    cursor,
                    bulkInsertContentValues[i]);
        }

        cursor.close();
    }

    @Test
    public void testDeleteAllCategoriesRecordsFromProvider() {
        testBulkInsertCategories();
        Uri contentUri = AchieversContract.Categories.buildCategoriesUri();

        testDeleteAllRecordsFromProvider(contentUri);
    }

    @Test
    public void testDeleteAllAchievementsRecordsFromProvider() {
        testBulkInsertCategories();
        Uri contentUri = AchieversContract.Achievements.buildAchievementsUri();

        testDeleteAllRecordsFromProvider(contentUri);
    }

    @Test
    public void testDeleteAllEvidenceRecordsFromProvider() {
        testBulkInsertCategories();
        Uri contentUri = AchieversContract.Evidence.buildEvidenceUri();

        testDeleteAllRecordsFromProvider(contentUri);
    }

    private void testDeleteAllRecordsFromProvider(Uri contentUri) {
        TestUtilities.TestContentObserver observer = TestUtilities.getTestContentObserver();

        ContentResolver contentResolver = mContext.getContentResolver();

        contentResolver.registerContentObserver(
                contentUri,
                true,
                observer);

        contentResolver.delete(
                contentUri,
                null, null);

        Cursor shouldBeEmptyCursor = contentResolver.query(
                contentUri,
                null, null, null, null);

        observer.waitForNotificationOrFail();

        contentResolver.unregisterContentObserver(observer);

        String cursorWasNull = "Cursor was null.";
        assertNotNull(cursorWasNull, shouldBeEmptyCursor);

        String allRecordsWereNotDeleted =
                "Error: All records were not deleted from weather table during delete";
        assertEquals(allRecordsWereNotDeleted,
                0,
                shouldBeEmptyCursor.getCount());

        shouldBeEmptyCursor.close();
    }

    /**
     * This method will clear all rows from the database.
     * <p>
     * Please note:
     * <p>
     * - This does NOT delete the tables itself. We call this method from our @Before annotated
     * method to clear all records from the database before each test on the ContentProvider.
     * <p>
     * - We don't use the ContentProvider's delete functionality to perform this row deletion
     * because in this class, we are attempting to test the ContentProvider. We can't assume
     * that our ContentProvider's delete method works in our ContentProvider's test class.
     */
    private void deleteAllRecordsFromDatabase() {
        /* Access writable database through WeatherDbHelper */
        AchieversDatabase dbHelper = new AchieversDatabase(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        database.delete(AchieversContract.Categories.TABLE_NAME, null, null);
        database.delete(AchieversContract.Achievements.TABLE_NAME, null, null);
        database.delete(AchieversContract.Evidence.TABLE_NAME, null, null);

        database.close();
    }
}