package com.achievers.utils;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Evidence;
import com.achievers.data.entities.EvidenceType;
import com.achievers.data.entities.Involvement;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import io.bloco.faker.Faker;

import static com.achievers.utils.Preconditions.checkNotNull;

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

    public String getImageUrl() {
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
        Involvement involvement = getInvolvement();
        String imageUrl = getImageUrl();

        return new Achievement(id, title, description, involvement, imageUrl);
    }

    public EvidenceType getEvidenceType() {
        checkNotNull(mRandom);

        List<EvidenceType> values = Arrays.asList(EvidenceType.values());
        int index = mRandom.nextInt(values.size());
        return values.get(index);
    }

    public Evidence getEvidence(long id, Date createdOn) {
        checkNotNull(mFaker);
        checkNotNull(mRandom);

        String comment = mFaker.lorem.sentence(5);
        EvidenceType evidenceType = getEvidenceType();
        String previewUrl = getImageUrl();
        String url;

        switch (evidenceType) {
            case Video:
                url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
                break;
            default:
                url = previewUrl;
                break;
        }

        return new Evidence(id, comment, evidenceType, previewUrl, url, createdOn);
    }
}
