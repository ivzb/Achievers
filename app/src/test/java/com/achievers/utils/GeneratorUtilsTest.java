package com.achievers.utils;

import android.net.Uri;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.AchievementProgress;
import com.achievers.data.entities.Evidence;
import com.achievers.data.entities.Involvement;
import com.achievers.data.entities.Quest;
import com.achievers.utils.ui.multimedia.MultimediaType;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import io.bloco.faker.Faker;
import io.bloco.faker.components.Lorem;

import static com.achievers.data.entities.Achievement.Type.Progressive;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneratorUtilsTest {

    private static String sFailMessageFormat = "Generated %s does not match";
    private static String sFailImage = String.format(sFailMessageFormat, "image");
    private static String sFailAchievement = String.format(sFailMessageFormat, "achievement");
    private static String sFailAchievementProgress = String.format(sFailMessageFormat, "achievement progress");
    private static String sFailEvidence = String.format(sFailMessageFormat, "evidence");
    private static String sFailQuest = String.format(sFailMessageFormat, "quest");

    private static final String sImagePathFormat = "https://unsplash.it/500/500/?random&a=%d";

    @Rule public MockitoRule mMockitoRule = MockitoJUnit.rule();

    @Mock private Random mRandom;
    @Mock private Faker mFaker;
    @Mock private Lorem mLorem;

    @Before
    public void before() {
        GeneratorUtils.initialize(mRandom, mFaker);
    }

    @After
    public void after() {
        GeneratorUtils.destroyInstance();
    }

    @Test(expected = NullPointerException.class)
    public void initialize_withNullRandom_shouldThrowNullPointer() {
        GeneratorUtils.initialize(null, mFaker);
    }

    @Test(expected = NullPointerException.class)
    public void initialize_withNullFaker_shouldThrowNullPointer() {
        GeneratorUtils.initialize(mRandom, null);
    }

    @Test
    public void initialize_properly_shouldReturnInstance() {
        GeneratorUtils.initialize(mRandom, mFaker);
        assertNotNull(GeneratorUtils.getInstance());
    }

    @Test(expected = NullPointerException.class)
    public void destroyInstance_getInstance_shouldThrow() {
        GeneratorUtils.destroyInstance();
        GeneratorUtils.getInstance();
    }

    @Test
    public void getAchievementType_shouldGenerateRandom() {
        Achievement.Type[] types = Achievement.Type.values();

        for (int i = 0; i < types.length; i++) {
            when(mRandom.nextInt(anyInt())).thenReturn(i);

            Achievement.Type expected = types[i];
            Achievement.Type actual = GeneratorUtils.getInstance().getEnum(types);

            assertEquals(expected, actual);
        }
    }

    @Test
    public void getMultimediaType_shouldGenerateRandom() {
        GeneratorUtils.initialize(mRandom, mFaker);

        MultimediaType[] types = MultimediaType.values();

        for (int i = 0; i < types.length; i++) {
            when(mRandom.nextInt(isA(int.class))).thenReturn(i);

            MultimediaType expected = types[i];
            MultimediaType actual = GeneratorUtils.getInstance().getEnum(types);

            assertEquals(sFailEvidence, expected, actual);
        }
    }

    @Test
    public void getInvolvement_shouldGenerateRandom() {
        Involvement[] involvements = Involvement.values();

        for (int i = 0; i < involvements.length; i++) {
            when(mRandom.nextInt(anyInt())).thenReturn(i);

            Involvement expected = involvements[i];
            Involvement actual = GeneratorUtils.getInstance().getEnum(involvements);

            assertEquals(expected, actual);
        }
    }

    @Test
    public void getImageUrl_shouldGenerateRandom() {
        // arrange
        int randomIndex = 3;
        when(mRandom.nextInt(isA(int.class))).thenReturn(randomIndex);

        String expected = String.format(sImagePathFormat, randomIndex);

        // act
        String actual = GeneratorUtils.getInstance().getImageUrl();

        // assert
        assertEquals(sFailImage, expected, actual);
    }

    @Test
    public void getAchievement_shouldGenerateRandom()
            throws NoSuchFieldException, IllegalAccessException {

        // arrange
        int randomIndex = 3;

        int id = 5;
        String title = "title";
        String description = "description";
        String imageUrl = String.format(sImagePathFormat, randomIndex);
        Involvement involvement = Involvement.Platinum;
        Date createdOn = getDate();

        when(mRandom.nextInt(isA(int.class))).thenReturn(randomIndex);
        when(mLorem.word()).thenReturn(title);
        when(mLorem.sentence(isA(int.class))).thenReturn(description);

        Field f = Faker.class.getDeclaredField("lorem");
        f.setAccessible(true);
        f.set(mFaker, mLorem);

        Achievement expected = new Achievement(
                id,
                title,
                description,
                involvement,
                Uri.parse(imageUrl),
                createdOn);

        // act
        Achievement actual = GeneratorUtils.getInstance().getAchievement(id, createdOn);

        // assert
        assertEquals(sFailAchievement, expected, actual);
    }

    @Test
    public void getAchievementProgress_shouldGenerateRandom() {
        // arrange
        long id = 5;
        long achievementId = 3;
        long userId = 7;
        Achievement.Type type = Progressive;
        int total = 13;
        int accomplished = 5;
        Date createdOn = getDate();

        when(mRandom.nextLong())
                .thenReturn(achievementId)
                .thenReturn(userId);

        when(mRandom.nextInt(anyInt()))
                .thenReturn(total)
                .thenReturn(accomplished)
                .thenReturn(type.ordinal());

        AchievementProgress expected = new AchievementProgress(
                id,
                achievementId,
                userId,
                type,
                total,
                accomplished,
                createdOn);

        // act
        AchievementProgress actual = GeneratorUtils.getInstance().getAchievementProgress(id, createdOn);

        // assert
        assertEquals(sFailAchievementProgress, expected, actual);
    }

    @Test
    public void getEvidence_photo()
            throws NoSuchFieldException, IllegalAccessException {

        getEvidence(MultimediaType.Photo);
    }

    @Test
    public void getEvidence_video()
            throws NoSuchFieldException, IllegalAccessException {

        getEvidence(MultimediaType.Video);
    }

    @Test
    public void getEvidence_voice()
            throws NoSuchFieldException, IllegalAccessException {

        getEvidence(MultimediaType.Voice);
    }

    private void getEvidence(MultimediaType type)
            throws NoSuchFieldException, IllegalAccessException {

        // arrange
        int randomIndex = 7;

        int id = 5;
        String comment = "faker sentence";
        String previewUrl = String.format(sImagePathFormat, randomIndex);

        String url;

        switch (type) {
            case Video:
                url = GeneratorUtils.sVideoPath;
                break;
            case Voice:
                url = GeneratorUtils.sVoicePath;
                break;
            default:
                url = previewUrl;
                break;
        }

        Date createdOn = getDate();

        when(mRandom.nextInt(isA(int.class)))
                .thenReturn(type.ordinal())
                .thenReturn(randomIndex);

        when(mLorem.sentence(anyInt())).thenReturn(comment);

        Field f = Faker.class.getDeclaredField("lorem");
        f.setAccessible(true);
        f.set(mFaker, mLorem);

        Evidence expected = new Evidence(
                id,
                comment,
                type,
                previewUrl, Uri.parse(url), createdOn);

        // act
        Evidence actual = GeneratorUtils.getInstance().getEvidence(id, createdOn);

        // assert
        assertEquals(sFailEvidence, expected, actual);
    }

    @Test
    public void getQuest()
            throws NoSuchFieldException, IllegalAccessException {

        // arrange
        long id = 5;
        String name = "quest";
        long[] achievementIds = new long[] { 4, 8, 13 };
        Date startedOn = getDate();

        when(mRandom.nextInt(anyInt()))
                .thenReturn(achievementIds.length)
                .thenReturn(4)
                .thenReturn(8)
                .thenReturn(13);

        when(mLorem.word()).thenReturn(name);

        Field f = Faker.class.getDeclaredField("lorem");
        f.setAccessible(true);
        f.set(mFaker, mLorem);

        Quest expected = new Quest(
                id,
                name,
                achievementIds,
                startedOn);

        // act
        Quest actual = GeneratorUtils.getInstance().getQuest(id, startedOn);

        // assert
        assertEquals(sFailQuest, expected, actual);
    }

    private Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 27);

        return calendar.getTime();
    }
}