package com.achievers.provider;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.BaseColumns;

import com.achievers.provider.AchieversContract.Achievements;
import com.achievers.provider.AchieversContract.Categories;
import com.achievers.provider.AchieversContract.Evidence;
import com.achievers.utils.PollingCheck;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

class TestUtilities {

    static final int ID_TO_INSERT = 1;
    static final int BULK_INSERT_RECORDS_TO_INSERT = 10;

    /**
     * Ensures there is a non empty cursor and validates the cursor's data by checking it against
     * a set of expected values. This method will then close the cursor.
     *
     * @param error          Message when an error occurs
     * @param valueCursor    The Cursor containing the actual values received from an arbitrary query
     * @param expectedValues The values we expect to receive in valueCursor
     */
    static void validateThenCloseCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertNotNull(
                "This cursor is null. Did you make sure to register your ContentProvider in the manifest?",
                valueCursor);

        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    /**
     * This method iterates through a set of expected values and makes various assertions that
     * will pass if our app is functioning properly.
     *
     * @param error          Message when an error occurs
     * @param valueCursor    The Cursor containing the actual values received from an arbitrary query
     * @param expectedValues The values we expect to receive in valueCursor
     */
    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int index = valueCursor.getColumnIndex(columnName);

            /* Test to see if the column is contained within the cursor */
            String columnNotFoundError = "Column '" + columnName + "' not found. " + error;
            assertFalse(columnNotFoundError, index == -1);

            /* Test to see if the expected value equals the actual value (from the Cursor) */
            Object expectedValue = entry.getValue();
            String actualValue = valueCursor.getString(index);

            String errorFormat = "Actual value '%s' did not match the expected value '%s'. %s";

            if (expectedValue == null) {
                assertEquals(String.format(errorFormat, actualValue, "null", error),
                        null,
                        actualValue);

                return;
            }

            String valuesDontMatchError = String.format(
                    errorFormat,
                    actualValue,
                    expectedValue.toString(),
                    error);

            assertEquals(valuesDontMatchError,
                    expectedValue.toString(),
                    actualValue);
        }
    }

    static ContentValues createTestCategoryContentValues(int id, boolean hasParentId) {
        ContentValues testCategoryValues = new ContentValues();

        testCategoryValues.put(Categories.CATEGORY_ID, id);
        testCategoryValues.put(Categories.CATEGORY_TITLE, "title" + id);
        testCategoryValues.put(Categories.CATEGORY_DESCRIPTION, "description" + id);
        testCategoryValues.put(Categories.CATEGORY_IMAGE_URL, "image url" + id);
        if (hasParentId) testCategoryValues.put(Categories.CATEGORY_PARENT_ID, id);

        return testCategoryValues;
    }

    static ContentValues[] createBulkInsertTestCategoryValues() {
        ContentValues[] bulkTestValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            int id = i + 1;
            boolean hasParentId = i % 2 == 0;
            ContentValues testCategoryValues = createTestCategoryContentValues(id, hasParentId);

            bulkTestValues[i] = testCategoryValues;
        }

        return bulkTestValues;
    }

    static ContentValues createTestAchievementContentValues(int id) {
        ContentValues testCategoryValues = new ContentValues();

        testCategoryValues.put(Achievements.ACHIEVEMENT_ID, id);
        testCategoryValues.put(Achievements.ACHIEVEMENT_TITLE, "title" + id);
        testCategoryValues.put(Achievements.ACHIEVEMENT_DESCRIPTION, "description" + id);
        testCategoryValues.put(Achievements.ACHIEVEMENT_IMAGE_URL, "image url" + id);
        testCategoryValues.put(Achievements.ACHIEVEMENT_CATEGORY_ID, id);
        testCategoryValues.put(Achievements.ACHIEVEMENT_INVOLVEMENT, id);
        testCategoryValues.put(Achievements.ACHIEVEMENT_AUTHOR_ID, id);

        return testCategoryValues;
    }

    static ContentValues[] createBulkInsertTestAchievementValues() {
        ContentValues[] bulkTestValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            int id = i + 1;
            ContentValues testCategoryValues = createTestAchievementContentValues(id);

            bulkTestValues[i] = testCategoryValues;
        }

        return bulkTestValues;
    }

    static ContentValues createTestEvidenceContentValues(int id) {
        ContentValues testCategoryValues = new ContentValues();

        testCategoryValues.put(Evidence.EVIDENCE_ID, id);
        testCategoryValues.put(Evidence.EVIDENCE_TITLE, "title" + id);
        testCategoryValues.put(Evidence.EVIDENCE_TYPE, id);
        testCategoryValues.put(Evidence.EVIDENCE_URL, "url" + id);
        testCategoryValues.put(Evidence.EVIDENCE_ACHIEVEMENT_ID, id);
        testCategoryValues.put(Evidence.EVIDENCE_AUTHOR_ID, id);

        return testCategoryValues;
    }

    static ContentValues[] createBulkInsertTestEvidenceValues() {
        ContentValues[] bulkTestValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            int id = i + 1;
            ContentValues testCategoryValues = createTestEvidenceContentValues(id);

            bulkTestValues[i] = testCategoryValues;
        }

        return bulkTestValues;
    }


    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }

    static void checkIfClassImplementsBaseColumns(Class cls) {
        if (!BaseColumns.class.isAssignableFrom(cls)) {
            String categoryEntryDoesNotImplementBaseColumns = cls.getSimpleName() + " class needs to " +
                    "implement the interface BaseColumns, but does not.";
            fail(categoryEntryDoesNotImplementBaseColumns);
        }
    }

    /**
     * The functions inside of TestProvider use TestContentObserver to test
     * the ContentObserver callbacks using the PollingCheck class from the Android Compatibility
     * Test Suite tests.
     * <p>
     * NOTE: This only tests that the onChange function is called; it DOES NOT test that the
     * correct Uri is returned.
     */
    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        /**
         * Called when a content change occurs.
         * <p>
         * To ensure correct operation on older versions of the framework that did not provide a
         * Uri argument, applications should also implement this method whenever they implement
         * the {@link #onChange(boolean, Uri)} overload.
         *
         * @param selfChange True if this is a self-change notification.
         */
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        /**
         * Called when a content change occurs. Includes the changed content Uri when available.
         *
         * @param selfChange True if this is a self-change notification.
         * @param uri        The Uri of the changed content, or null if unknown.
         */
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        /**
         * Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
         * It's useful to look at the Android CTS source for ideas on how to test your Android
         * applications. The reason that PollingCheck works is that, by default, the JUnit testing
         * framework is not running on the main Android application thread.
         */
        void waitForNotificationOrFail() {

            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static String getConstantNameByStringValue(Class klass, String value)  {
        for (Field f : klass.getDeclaredFields()) {
            int modifiers = f.getModifiers();
            Class<?> type = f.getType();
            boolean isPublicStaticFinalString = Modifier.isStatic(modifiers)
                    && Modifier.isFinal(modifiers)
                    && Modifier.isPublic(modifiers)
                    && type.isAssignableFrom(String.class);

            if (isPublicStaticFinalString) {
                String fieldName = f.getName();
                try {
                    String fieldValue = (String) klass.getDeclaredField(fieldName).get(null);
                    if (fieldValue.equals(value)) return fieldName;
                } catch (IllegalAccessException e) {
                    return null;
                } catch (NoSuchFieldException e) {
                    return null;
                }
            }
        }

        return null;
    }

    static String getStaticStringField(Class clazz, String variableName)
            throws NoSuchFieldException, IllegalAccessException {
        Field stringField = clazz.getDeclaredField(variableName);
        stringField.setAccessible(true);
        String value = (String) stringField.get(null);
        return value;
    }

    static Integer getStaticIntegerField(Class clazz, String variableName)
            throws NoSuchFieldException, IllegalAccessException {
        Field intField = clazz.getDeclaredField(variableName);
        intField.setAccessible(true);
        Integer value = (Integer) intField.get(null);
        return value;
    }

    static String readableClassNotFound(ClassNotFoundException e) {
        String message = e.getMessage();
        int indexBeforeSimpleClassName = message.lastIndexOf('.');
        String simpleClassNameThatIsMissing = message.substring(indexBeforeSimpleClassName + 1);
        simpleClassNameThatIsMissing = simpleClassNameThatIsMissing.replaceAll("\\$", ".");
        String fullClassNotFoundReadableMessage = "Couldn't find the class "
                + simpleClassNameThatIsMissing
                + ".\nPlease make sure you've created that class.";
        return fullClassNotFoundReadableMessage;
    }

    static String readableNoSuchField(NoSuchFieldException e) {
        String message = e.getMessage();

        Pattern p = Pattern.compile("No field (\\w*) in class L.*/(\\w*\\$?\\w*);");

        Matcher m = p.matcher(message);

        if (m.find()) {
            String missingFieldName = m.group(1);
            String classForField = m.group(2).replaceAll("\\$", ".");
            String fieldNotFoundReadableMessage = "Couldn't find "
                    + missingFieldName + " in class " + classForField + "."
                    + "\nPlease make sure you've declared that field.";
            return fieldNotFoundReadableMessage;
        } else {
            return e.getMessage();
        }
    }
}