package com.achievers.utils;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Involvement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import io.bloco.faker.Faker;
import io.bloco.faker.components.Lorem;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;

@RunWith(MockitoJUnitRunner.class)
public class GeneratorUtilsTest {

    private static String sFailMessageFormat = "Generated %s does not match";
    private static String sFailImage = String.format(sFailMessageFormat, "image");
    private static String sFailInvolvement = String.format(sFailMessageFormat, "involvement");
    private static String sFailAchievement = String.format(sFailMessageFormat, "achievement");
    private static final String sImagePathFormat = "https://unsplash.it/500/500/?random&a=%d";

    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();

    @Mock
    private Random mRandom;

    @Mock
    private Faker mFaker;

    @Mock
    private Lorem mLorem;

    @Test(expected = NullPointerException.class)
    public void getImage_withNullRandom_shouldThrowNullPointer() {
        GeneratorUtils.initialize(null, mFaker);
        GeneratorUtils.getInstance().getImageUrl();
    }

    @Test(expected = NullPointerException.class)
    public void getInvolvement_withNullRandom_shouldThrowNullPointer() {
        GeneratorUtils.initialize(null, mFaker);
        GeneratorUtils.getInstance().getInvolvement();
    }

    @Test
    public void getImage_shouldGenerateRandom() {
        GeneratorUtils.initialize(mRandom, mFaker);

        int randomIndex = 3;
        Mockito.when(mRandom.nextInt(isA(int.class))).thenReturn(randomIndex);

        String expected = String.format(sImagePathFormat, randomIndex);
        String actual = GeneratorUtils.getInstance().getImageUrl();

        assertEquals(sFailImage, expected, actual);
    }

    @Test
    public void getInvolvement_shouldGenerateRandom() {
        GeneratorUtils.initialize(mRandom, mFaker);

        Involvement[] expected = Involvement.values();

        for (int i = 0; i < expected.length; i++) {
            Mockito.when(mRandom.nextInt(isA(int.class))).thenReturn(i);
            Involvement actual = GeneratorUtils.getInstance().getInvolvement();

            assertEquals(sFailInvolvement, expected[i], actual);
        }
    }

    @Test
    public void getAchievement_shouldGenerateRandom() throws NoSuchFieldException, IllegalAccessException {
        GeneratorUtils.initialize(mRandom, mFaker);

        int randomIndex = 3;

        int id = 5;
        String title = "title";
        String description = "title";
        String imageUrl = String.format(sImagePathFormat, randomIndex);
        Involvement involvement = Involvement.Platinum;
        Date createdOn = getDate();

        Mockito.when(mRandom.nextInt(isA(int.class))).thenReturn(randomIndex);

        Mockito.when(mLorem.word()).thenReturn(title);
        Mockito.when(mLorem.sentence(isA(int.class))).thenReturn(description);

        Field f = Faker.class.getDeclaredField("lorem");
        f.setAccessible(true);
        f.set(mFaker, mLorem);

        Achievement expected = new Achievement(5, title, description, involvement, imageUrl);
        Achievement actual = GeneratorUtils.getInstance().getAchievement(id, createdOn);

        assertEquals(sFailAchievement, expected, actual);
    }

    private Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 27);

        return calendar.getTime();
    }
}