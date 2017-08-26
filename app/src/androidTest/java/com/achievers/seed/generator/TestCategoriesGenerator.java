package com.achievers.seed.generator;

import android.support.test.runner.AndroidJUnit4;

import com.achievers.entities.Category;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class TestCategoriesGenerator {

    private static final int InitialId = 1;

    private CategoriesGenerator mGenerator;

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

    @Test
    public void testMultipleParent1Child0() {
        int[] sizes = new int[] { 1 };
        testMultiple(sizes);
    }

    @Test
    public void testMultipleParent2Child0() {
        int[] sizes = new int[] { 2 };
        testMultiple(sizes);
    }

    @Test
    public void testMultipleParent3Child0() {
        int[] sizes = new int[] { 3 };
        testMultiple(sizes);
    }

    @Test
    public void testMultipleParent1Child1() {
        int[] sizes = new int[] { 1, 1 };
        testMultiple(sizes);
    }

    @Test
    public void testMultipleParent1Child3() {
        int[] sizes = new int[] { 1, 3 };
        testMultiple(sizes);
    }

    @Test
    public void testMultipleParent5Child5() {
        int[] sizes = new int[] { 3, 5 };
        testMultiple(sizes);
    }

    @Test
    public void testMultipleParent2Child3Child4() {
        int[] sizes = new int[] { 2, 3, 4 };

        testMultiple(sizes);
    }

    @Test
    public void testMultipleParent2Child3Child45() {
        int[] sizes = new int[] { 2, 3, 4, 5 };

        testMultiple(sizes);
    }

    private List<Category> generateOneDimensions(int[] sizes) {
        List<Category> expected = new ArrayList<>();

        int lastId = 0;

        for (int i = 0; i < sizes[0]; i++) {
            expected.add(new Category(++lastId, null));
        }

        return expected;
    }

    private List<Category> generateTwoDimensions(int[] sizes) {
        List<Category> expected = new ArrayList<>();

        int lastId = 0;

        for (int i = 0; i < sizes[0]; i++) {
            Category root = new Category(++lastId, null);
            expected.add(root);
            Integer parentId = root.getId();

            for (int j = 0; j < sizes[1]; j++) {
                expected.add(new Category(++lastId, parentId));
            }
        }

        return expected;
    }

    private List<Category> generateThreeDimensions(int[] sizes) {
        List<Category> expected = new ArrayList<>();

        int lastId = 0;

        for (int i = 0; i < sizes[0]; i++) {
            Category root = new Category(++lastId, null);
            expected.add(root);
            Integer parentId = root.getId();

            for (int j = 0; j < sizes[1]; j++) {
                Category child = new Category(++lastId, parentId);
                expected.add(child);
                Integer childParentId = child.getId();

                for (int k = 0; k < sizes[2]; k++) {
                    expected.add(new Category(++lastId, childParentId));
                }
            }
        }

        return expected;
    }

    private List<Category> generateFourDimensions(int[] sizes) {
        List<Category> expected = new ArrayList<>();

        int lastId = 0;

        for (int i = 0; i < sizes[0]; i++) {
            Category root = new Category(++lastId, null);
            expected.add(root);
            Integer parentId = root.getId();

            for (int j = 0; j < sizes[1]; j++) {
                Category child = new Category(++lastId, parentId);
                expected.add(child);
                Integer childParentId = child.getId();

                for (int k = 0; k < sizes[2]; k++) {
                    Category childChild = new Category(++lastId, childParentId);
                    expected.add(childChild);
                    Integer childChildParentId = childChild.getId();

                    for (int l = 0; l < sizes[3]; l++) {
                        expected.add(new Category(++lastId, childChildParentId));
                    }
                }
            }
        }

        return expected;
    }

    private void testMultiple(int[] sizes) {
        List<Category> expected = null;

        switch (sizes.length) {
            case 1:
                expected = generateOneDimensions(sizes);
                break;
            case 2:
                expected = generateTwoDimensions(sizes);
                break;
            case 3:
                expected = generateThreeDimensions(sizes);
                break;
            case 4:
                expected = generateFourDimensions(sizes);
        }

        List<Category> actual = mGenerator.multiple(sizes);

        assertEquals("The generated categories differ from expected.",
                expected,
                actual);
    }
}