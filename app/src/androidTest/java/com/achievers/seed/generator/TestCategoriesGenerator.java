package com.achievers.seed.generator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.achievers.entities.Category;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TestCategoriesGenerator {

    private static final int InitialId = 1;

    CategoriesGenerator mGenerator;

    @Before
    public void before() {
       mGenerator = new CategoriesGenerator();
    }

    @Test
    public void testGeneratingSingleCategoryWithCorrectCategory() {
        Category expectedCategory = new Category(InitialId, "title", "description", "http://image.com", null);

        Category generatedCategory = mGenerator.single();

        assertEquals("The categories aren't equal but should be",
                expectedCategory,
                generatedCategory);
    }

    @Test
    public void testGeneratingSingleCategoryWithNull() {
        Category generatedCategory = mGenerator.single();

        assertNotNull("The generated category shouldn't be null", generatedCategory);
    }
}