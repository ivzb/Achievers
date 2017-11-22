package com.achievers.utils;

import android.net.Uri;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.AchievementProgress;
import com.achievers.data.entities.Evidence;
import com.achievers.data.entities.Involvement;
import com.achievers.data.entities.Quest;
import com.achievers.utils.ui.multimedia.MultimediaType;

import java.util.Date;
import java.util.Locale;
import java.util.Random;

import io.bloco.faker.Faker;

import static com.achievers.utils.Preconditions.checkNotNull;

public class GeneratorUtils {

    private final Random mRandom;
    private final Faker mFaker;

    private static GeneratorUtils sInstance;

    private static final String sImagePathFormat = "https://unsplash.it/500/500/?random&a=%d";
    public static final String sVideoPath = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    public static final String sVoicePath = "https://archive.org/download/testmp3testfile/mpthreetest.mp3";

    private GeneratorUtils(Random random, Faker faker) {
        mRandom = checkNotNull(random);
        mFaker = checkNotNull(faker);
    }

    public static void initialize(Random random, Faker faker) {
        sInstance = new GeneratorUtils(random, faker);
    }

    public static GeneratorUtils getInstance() {
        return checkNotNull(sInstance);
    }

    public static void destroyInstance() {
        sInstance = null;
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

    public Achievement getAchievement(long id, Date createdOn) {
        String title = mFaker.lorem.word();
        String description = mFaker.lorem.sentence(5);
        Involvement involvement = getEnum(Involvement.values());
        Uri imageUri = getImageUri();

        return new Achievement(id, title, description, involvement, imageUri, createdOn);
    }

    public AchievementProgress getAchievementProgress(long id, Date createdOn) {
        long achievementId = mRandom.nextLong();
        long userId = mRandom.nextLong();
        int total = mRandom.nextInt(25) + 1;
        int accomplished = mRandom.nextInt(total) + 1;
        Achievement.Type type = getEnum(Achievement.Type.values());

        return new AchievementProgress(
                id,
                achievementId,
                userId,
                type,
                total,
                accomplished,
                createdOn);
    }

    public Evidence getEvidence(long id, Date createdOn) {
        String comment = mFaker.lorem.sentence(5);
        MultimediaType multimediaType = getEnum(MultimediaType.values());
        String previewUrl = getImageUrl();
        String url;

        switch (multimediaType) {
            case Video:
                url = sVideoPath;
                break;
            case Voice:
                url = sVoicePath;
                break;
            default:
                url = previewUrl;
                break;
        }

        return new Evidence(id, comment, multimediaType, previewUrl, Uri.parse(url), createdOn);
    }

    public Quest getQuest(long id, Date startedOn) {
        String name = mFaker.lorem.word();
        Uri picture = getImageUri();
        int achievementsCount = mRandom.nextInt(15) + 1;

        return new Quest(id, name, picture, achievementsCount, startedOn);
    }
}
