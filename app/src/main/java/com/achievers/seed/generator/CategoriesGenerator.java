package com.achievers.seed.generator;

import com.achievers.entities.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.bloco.faker.Faker;

public class CategoriesGenerator implements Generator<Category> {

    private static final int InitialIndex = 0;
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
                0,
                0,
                RootParent,
                new ArrayList<Category>());
    }

    private List<Category> generate(
            int[] sizes,
            int row,
            int col,
            Integer parentId,
            List<Category> generated) {

//        if (currentSize > 0) {
//        for (int size = 0; size < sizes[index]; size++) {
        int lastId = 0;

        if (generated.size() > 0) lastId = generated.get(generated.size() - 1).getId();
        Category category = generateCategory(lastId + 1, parentId);
        generated.add(category);

//        if (sizes.length > row + 1) {
//            generated.addAll(generate(sizes, row + 1, 0, category.getId(), generated));
//        }

        if (sizes[row] > col + 1) {
            generated.addAll(generate(sizes, row, col + 1, parentId, generated));
        }

//        if (sizes.length > row + 1) {
//            List<Category> inner = generate(sizes, row + 1, 0, lastId, category.getId(), generated);
//            generated.addAll(inner);
//        }
//        }

//        }

//        return generate(sizes, row, ++col, lastId, parentId, generated);
        return generated;
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
