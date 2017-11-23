package com.achievers.data.generators;

import com.achievers.data.entities.Category;
import com.achievers.data.generators.config.GeneratorConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

public class CategoriesGenerator {

    private static final int InitialRow = 0;
    private static final int InitialCol = 0;
    private static final Long RootParent = null;

    private final Faker mFaker;
    private final Random mRandom;

    public CategoriesGenerator() {
        mFaker = new Faker();
        mRandom = new Random();
    }

    public Category single(long id, Long parentId) {
        return generateCategory(id, parentId);
    }

    public List<Category> multiple(int[] sizes) {
        return generate(
                sizes,
                InitialRow,
                InitialCol,
                RootParent,
                new ArrayList<Category>());
    }

    private List<Category> generate(
            int[] sizes,
            int row,
            int col,
            Long parentId,
            List<Category> generated) {

        long id = getId(generated);
        generated.add(generateCategory(id, parentId));

        if (sizes.length > row + 1) {
            generate(sizes, row + 1, InitialCol, id, generated);
        }

        if (sizes[row] > col + 1) {
            generate(sizes, row, col + 1, parentId, generated);
        }

        return generated;
    }

    private long getId(List<Category> categories) {
        int size = categories.size();
        long lastId = size > 0 ? categories.get(size - 1).getId() : 0;

        return lastId + 1;
    }

    private Category generateCategory(long id, Long parentId) {
        String title = mFaker.lorem.word();
        String description = mFaker.lorem.sentence(5);
        String imageUrl = GeneratorConfig.getInstance().getImageUrl();

        return new Category(id, title, description, imageUrl, parentId);
    }
}
