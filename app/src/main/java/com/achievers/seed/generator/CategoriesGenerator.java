package com.achievers.seed.generator;

import com.achievers.entities.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.bloco.faker.Faker;

public class CategoriesGenerator implements Generator<Category> {

    private static final int InitialRow = 0;
    private static final int InitialCol = 0;
    private static final int InitialId = 0;
    private static final Integer RootParent = null;
    private static final String RandomImagePath = "https://unsplash.it/500/500/?random&a=%d";

    private final Faker mFaker;

    public CategoriesGenerator() {
        mFaker = new Faker();
    }

    @Override
    public Category single() {
        return generateCategory(InitialId + 1, null);
    }

    @Override
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
            Integer parentId,
            List<Category> generated) {

        int id = getId(generated);
        generated.add(generateCategory(id, parentId));

        if (sizes.length > row + 1) {
            generate(sizes, row + 1, InitialCol, id, generated);
        }

        if (sizes[row] > col + 1) {
            generate(sizes, row, col + 1, parentId, generated);
        }

        return generated;
    }

    private int getId(List<Category> categories) {
        int size = categories.size();
        int lastId = size > 0 ? categories.get(size - 1).getId() : 0;

        return lastId + 1;
    }

    private Category generateCategory(int id, Integer parentId) {
        String title = mFaker.lorem.word();
        String description = mFaker.lorem.sentence(5);
        String imageUrl =  String.format(
                Locale.getDefault(),
                RandomImagePath,
                mFaker.number.between(0, 100));

        return new Category(id, title, description, imageUrl, parentId);
    }
}
