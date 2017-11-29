package com.achievers.data.generators.config;

import android.net.Uri;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.generators._base.BaseGeneratorConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TreeSet;

import io.bloco.faker.Faker;

import static com.achievers.utils.Preconditions.checkNotNull;

public class GeneratorConfig implements BaseGeneratorConfig {

    private static GeneratorConfig sInstance;
    private static final String sImagePathFormat = "https://unsplash.it/500/500/?random&a=%d";
    public static final String sVideoPath = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    public static final String sVoicePath = "https://archive.org/download/testmp3testfile/mpthreetest.mp3";

    private final Random mRandom;
    private final Faker mFaker;

    private GeneratorConfig(Random random, Faker faker) {
        mRandom = checkNotNull(random);
        mFaker = checkNotNull(faker);
    }

    public static void initialize(Random random, Faker faker) {
        sInstance = new GeneratorConfig(random, faker);
    }

    public static GeneratorConfig getInstance() {
        return checkNotNull(sInstance);
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public String getWord() {
        return mFaker.lorem.word();
    }

    @Override
    public String getSentence() {
        return mFaker.lorem.sentence(5);
    }

    @Override
    public long getId() {
        return mRandom.nextLong();
    }

    @Override
    public int getNumber() {
        return mRandom.nextInt();
    }

    @Override
    public int getNumber(int bound) {
        return Math.max(mRandom.nextInt(bound), 1);
    }

    @Override
    public <T extends Enum<T>> T getEnum(T[] types) {
        int selected = mRandom.nextInt(types.length);
        return types[selected];
    }

    @Override
    public String getImageUrl() {
        return String.format(
                Locale.getDefault(),
                sImagePathFormat,
                mRandom.nextInt(100));
    }

    @Override
    public Uri getImageUri() {
        return Uri.parse(getImageUrl());
    }

    @Override
    public String getVideoUrl() {
        return sVideoPath;
    }

    @Override
    public Uri getVideoUri() {
        return Uri.parse(getVideoUrl());
    }

    @Override
    public String getVoiceUrl() {
        return sVoicePath;
    }

    @Override
    public Uri getVoiceUri() {
        return Uri.parse(getVoiceUrl());
    }

    @Override
    public Date getDate() {
        return new Date();
    }

    @Override
    public <T extends BaseModel> List<T> getAmong(List<T> entities) {
        final List<T> results = new ArrayList<>();

        getAmong(entities, new AmongCallback<T>() {
            @Override
            public void add(T entity) {
                results.add(entity);
            }
        });

        return results;
    }

    @Override
    public <T extends BaseModel> TreeSet<Long> getIdsAmong(final List<T> entities) {
        final TreeSet<Long> results = new TreeSet<>();

        getAmong(entities, new AmongCallback<T>() {
            @Override
            public void add(T entity) {
                results.add(entity.getId());
            }
        });

        return results;
    }

    private <T> void getAmong(List<T> entities, AmongCallback<T> callback) {
        int entitiesSize = entities.size();
        int resultsSize = getNumber(entitiesSize);
        int interval = entitiesSize / resultsSize;

        for (int i = 0; i < resultsSize; i++) {
            T entity = entities.get(i * interval);
            callback.add(entity);
        }
    }

    private interface AmongCallback<T> {
        void add(T entity);
    }
}