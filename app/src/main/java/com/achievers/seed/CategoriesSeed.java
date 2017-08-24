package com.achievers.seed;

import com.achievers.entities.Category;
import com.achievers.seed.generator.CategoriesGenerator;
import com.achievers.seed.generator.Generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.bloco.faker.Faker;

public class CategoriesSeed implements Seed<Category> {

    private static final int RootCategoriesCount = 5;

    private Faker mFaker;
    private List<Category> mEntities;

    public CategoriesSeed() {
        this.mFaker = new Faker();
        this.mEntities = new ArrayList<>();
    }

    @Override
    public List<Category> getData() {
//        String title = mFaker.lorem.word();
//        String description = mFaker.lorem.sentence(5);
//        String imageUrl =  String.format(
//                Locale.getDefault(),
//                RandomImagePath,
//                mFaker.number.between(0, 100));

        if (shouldGenerate()) {
            Generator<Category> generator = new CategoriesGenerator();
            mEntities = generator.multiple(RootCategoriesCount);
        }

        return mEntities;
    }

    private boolean shouldGenerate() {
        return mEntities.size() == 0;
    }



    private void save(Category category) {
        mEntities.add(category);
    }
}
