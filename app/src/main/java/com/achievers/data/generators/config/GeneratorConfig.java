package com.achievers.data.generators.config;

import android.net.Uri;

import com.achievers.data.generators._base.BaseGeneratorConfig;

import java.util.Date;
import java.util.Locale;
import java.util.Random;

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

    public String getWord() {
        return mFaker.lorem.word();
    }

    public String getSentence() {
        return mFaker.lorem.sentence(5);
    }

    public long getId() {
        return mRandom.nextLong();
    }

    public int getNumber() {
        return mRandom.nextInt();
    }

    public int getNumber(int bound) {
        return mRandom.nextInt(bound) + 1;
    }

    public <T extends Enum<T>> T getEnum(T[] types) {
        int selected = mRandom.nextInt(types.length);
        return types[selected];
    }

    public String getImageUrl() {
        return String.format(
                Locale.getDefault(),
                sImagePathFormat,
                mRandom.nextInt(100));
    }

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

    public Date getDate() {
        return new Date();
    }
}