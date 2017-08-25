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
        int lastId = 0;

        List<Category> expected = new ArrayList<>();
        Integer parentId = null;
        expected.add(new Category(++lastId, parentId));

        testMultiple(expected, sizes);
    }

    @Test
    public void testMultipleParent2Child0() {
        int[] sizes = new int[] { 2 };
        int lastId = 0;

        List<Category> expected = new ArrayList<>();
        Integer parentId = null;
        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));

        testMultiple(expected, sizes);
    }

    @Test
    public void testMultipleParent3Child0() {
        int[] sizes = new int[] { 3 };
        int lastId = 0;

        List<Category> expected = new ArrayList<>();
        Integer parentId = null;
        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));

        testMultiple(expected, sizes);
    }

    @Test
    public void testMultipleParent1Child1() {
        int[] sizes = new int[] { 1, 1 };
        int lastId = 0;

        List<Category> expected = new ArrayList<>();
        Integer parentId = null;
        expected.add(new Category(++lastId, parentId));

        parentId = lastId;
        expected.add(new Category(++lastId, parentId));

        testMultiple(expected, sizes);
    }

    @Test
    public void testMultipleParent1Child3() {
        int[] sizes = new int[] { 1, 3 };
        int lastId = 0;

        List<Category> expected = new ArrayList<>();
        Integer parentId = null;
        expected.add(new Category(++lastId, parentId));

        parentId = lastId;
        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));

        testMultiple(expected, sizes);
    }

    @Test
    public void testMultipleParent5Child5() {
        int[] sizes = new int[] { 3, 5 };
        int lastId = 0;

        List<Category> expected = new ArrayList<>();

        Category root = new Category(++lastId, null);
        expected.add(root);
        Integer parentId = root.getId();

        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));

        root = new Category(++lastId, null);
        expected.add(root);
        parentId = root.getId();

        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));

        root = new Category(++lastId, null);
        expected.add(root);
        parentId = root.getId();

        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));
        expected.add(new Category(++lastId, parentId));

        testMultiple(expected, sizes);
    }

    @Test
    public void testMultipleParent2Child3Child4() {
        int[] sizes = new int[] { 2, 3, 4 };

        List<Category> expected = new ArrayList<>();

        int lastId = 0;

        List<Category> c = add(sizes[0], null, lastId, new ArrayList<Category>());
        lastId += c.size();
        expected.addAll(c);

        for (int i = 0; i < c.size(); i++) {
            Integer parentId = c.get(i).getId();
            List<Category> d = add(sizes[1], parentId, lastId, new ArrayList<Category>());
            lastId += d.size();
            expected.addAll(d);

            for (int j = 0; j < d.size(); j++) {
                Integer pId = d.get(j).getId();
                List<Category> e = add(sizes[2], pId, lastId, new ArrayList<Category>());
                lastId += e.size();
                expected.addAll(e);
            }
        }

        testMultiple(expected, sizes);
    }

    private List<Category> add(int n, Integer parentId, int lastId, List<Category> categories) {
        if (n == 0) return categories;

        categories.add(new Category(++lastId, parentId));

        return add(--n, parentId, lastId, categories);
    }

    private void testMultiple(List<Category> expected, int[] sizes) {
//        List<Category> expected = new ArrayList<>();
//
//        int lastId = 0;
//        Integer parentId = null;
//
//        for (int size: sizes) {
//            for (int i = 0; i < size; i++) {
//                Category category = new Category(++lastId, parentId);
//                expected.add(category);
//
//                if (i + 1 == size) {
//                    parentId = category.getId();
//                }
//            }
//        }

        List<Category> actual = mGenerator.multiple(sizes);

        assertEquals("The generated categories differ from expected.",
                expected,
                actual);
    }
}