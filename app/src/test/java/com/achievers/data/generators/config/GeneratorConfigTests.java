package com.achievers.data.generators.config;

import android.net.Uri;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Involvement;
import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.data.generators._base.BaseGeneratorConfig;
import com.achievers.utils.ui.multimedia.MultimediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Random;

import io.bloco.faker.Faker;
import io.bloco.faker.components.Lorem;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneratorConfigTests {

    private static final String sImagePathFormat = "https://unsplash.it/500/500/?random&a=%d";

    @Mock private Random mRandom;
    @Mock private Faker mFaker;
    @Mock private Lorem mLorem;

    private BaseGeneratorConfig mConfig;

    @Before
    public void before() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(mRandom, mFaker);

        mConfig = GeneratorConfig.getInstance();
    }

    @Test(expected = NullPointerException.class)
    public void initialize_withNullRandom_shouldThrowNullPointer() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(null, mFaker);
    }

    @Test(expected = NullPointerException.class)
    public void initialize_withNullFaker_shouldThrowNullPointer() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(mRandom, null);
    }

    @Test
    public void initialize_properly_shouldReturnInstance() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(mRandom, mFaker);
        assertNotNull(GeneratorConfig.getInstance());
    }

    @Test(expected = NullPointerException.class)
    public void destroyInstance_getInstance_shouldThrow() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.getInstance();
    }

    @Test
    public void getWord()
            throws NoSuchFieldException, IllegalAccessException {

        String expected = "word";
        when(mLorem.word()).thenReturn(expected);
        setupFaker();

        // act
        String actual = mConfig.getWord();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void getSentence()
            throws NoSuchFieldException, IllegalAccessException {

        // arrange
        String expected = "sentence";
        when(mLorem.sentence(anyInt())).thenReturn(expected);
        setupFaker();

        // act
        String actual = mConfig.getSentence();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void getId() {
        // arrange
        long expected = 5L;
        when(mRandom.nextLong()).thenReturn(expected);

        // act
        long actual = mConfig.getId();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void getNumber() {
        // arrange
        int expected = 42;
        when(mRandom.nextInt()).thenReturn(expected);

        // act
        int actual = mConfig.getNumber();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void getNumberBound() {
        // arrange
        int expected = 42;
        when(mRandom.nextInt(anyInt())).thenReturn(expected);

        // act
        int actual = mConfig.getNumber(5);

        // assert
        assertEquals(expected, actual);
    }

    private void setupFaker()
            throws NoSuchFieldException, IllegalAccessException {

        Field f = Faker.class.getDeclaredField("lorem");
        f.setAccessible(true);
        f.set(mFaker, mLorem);
    }

    @Test
    public void getEnum_involvement() {
        testEnum(Involvement.values());
    }

    @Test
    public void getEnum_achievementTypes() {
        testEnum(Achievement.Type.values());
    }

    @Test
    public void getEnum_multimediaType() {
        testEnum(MultimediaType.values());
    }

    @Test
    public void getEnum_questType() {
        testEnum(Quest.Type.values());
    }

    @Test
    public void getEnum_rewardType() {
        testEnum(Reward.Type.values());
    }

    private <E extends Enum<E>> void testEnum(E[] values) {
        for (int i = 0; i < values.length; i++) {
            when(mRandom.nextInt(anyInt())).thenReturn(i);

            E expected = values[i];
            E actual = mConfig.getEnum(values);

            assertEquals(expected, actual);
        }
    }

    @Test
    public void getImageUrl() {
        // arrange
        int imageId = 3;
        when(mRandom.nextInt(anyInt())).thenReturn(imageId);

        String expected = String.format(sImagePathFormat, imageId);

        // act
        String actual = mConfig.getImageUrl();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void getImageUri() {
        // arrange
        int imageId = 3;
        when(mRandom.nextInt(anyInt())).thenReturn(imageId);

        Uri expected = Uri.parse(String.format(sImagePathFormat, imageId));

        // act
        Uri actual = mConfig.getImageUri();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void getVideoUrl() {
        // arrange
        String expected = GeneratorConfig.sVideoPath;

        // act
        String actual = mConfig.getVideoUrl();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void getVideoUri() {
        // arrange
        Uri expected = Uri.parse(GeneratorConfig.sVideoPath);

        // act
        Uri actual = mConfig.getVideoUri();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void getVoiceUrl() {
        // arrange
        String expected = GeneratorConfig.sVoicePath;

        // act
        String actual = mConfig.getVoiceUrl();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void getVoiceUri() {
        // arrange
        Uri expected = Uri.parse(GeneratorConfig.sVoicePath);

        // act
        Uri actual = mConfig.getVoiceUri();

        // assert
        assertEquals(expected, actual);
    }
}