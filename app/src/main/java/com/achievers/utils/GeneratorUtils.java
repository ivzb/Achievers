package com.achievers.utils;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Involvement;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import io.bloco.faker.Faker;

import static com.google.common.base.Preconditions.checkNotNull;

public class GeneratorUtils {

    private final Random mRandom;
    private final Faker mFaker;

    private static GeneratorUtils sInstance;

    private static final String sImagePathFormat = "https://unsplash.it/500/500/?random&a=%d";

    private GeneratorUtils(Random random, Faker faker) {
        mRandom = random;
        mFaker = faker;
    }

    public static void initialize(Random random, Faker faker) {
        sInstance = new GeneratorUtils(random, faker);
    }

    public static GeneratorUtils getInstance() {
        return sInstance;
    }

    public String getImage() {
        checkNotNull(mRandom);

        return String.format(
                Locale.getDefault(),
                sImagePathFormat,
                mRandom.nextInt(100));
    }

    public Involvement getInvolvement() {
        checkNotNull(mRandom);

        List<Involvement> values = Arrays.asList(Involvement.values());
        int index = mRandom.nextInt(values.size());
        return values.get(index);
    }

    public Achievement getAchievement(long id, Date createdOn) {
        checkNotNull(mFaker);
        checkNotNull(mRandom);

        String title = mFaker.lorem.word();
        String description = mFaker.lorem.sentence(5);
        String imageUrl = getImage();
        Involvement involvement = getInvolvement();

        return new Achievement(id, title, description, imageUrl, involvement, createdOn);
    }
}
