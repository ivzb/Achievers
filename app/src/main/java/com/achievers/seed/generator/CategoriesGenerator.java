package com.achievers.seed.generator;

import com.achievers.entities.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.bloco.faker.Faker;

public class CategoriesGenerator implements Generator<Category> {

    private static final int InitialId = 1;
    private static final int ChildrenCount = 3;
    private static final String RandomImagePath = "https://unsplash.it/500/500/?random&a=%d";

    private final Faker mFaker;

    public CategoriesGenerator() {
        mFaker = new Faker();
    }

    @Override
    public Category single() {
        return generateCategory(InitialId, null);
    }

    @Override
    public List<Category> multiple(int size) {
        return generateCategories(size, InitialId, new ArrayList<Category>());
    }

    private List<Category> generateCategories(int size, int lastId, List<Category> generated) {
        if (size == 0) return generated;

        Category parent = generateCategory(lastId++, null);

        for (int i = 0; i < ChildrenCount; i++) {
            Category child = generateCategory(lastId++, parent.getId());
            generated.add(child);

            //for (int j = 0; j < 3; j++) GenerateCategory(child);
        }

        return generateCategories(--size, lastId, generated);
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
