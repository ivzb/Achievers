package com.achievers.generator;

import com.achievers.data.entities.AchievementProgress;
import com.achievers.generator._base.BaseGenerator;
import com.achievers.utils.GeneratorUtils;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

import static junit.framework.Assert.assertEquals;

public class AchievementsProgressGeneratorTest {

    private static String sFailMessage = "Generated achievement progress does not match";

    private BaseGenerator<AchievementProgress> mGenerator;

    @Before
    public void before() {
        GeneratorUtils.initialize(new Random(), new Faker());
        mGenerator = new AchievementsProgressGenerator();
    }

    @Test
    public void testGeneratingSingleAchievementProgress() {
        // arrange
        long id = 5;
        AchievementProgress expected = new AchievementProgress(id);

        // act
        AchievementProgress actual = mGenerator.single(id);

        // assert
        assertEquals(sFailMessage, expected.getId(), actual.getId());
    }

    @Test
    public void testGeneratingMultipleEvidence() {
        // arrange
        long id = 5;
        int size = 5;

        // act
        List<AchievementProgress> actual = mGenerator.multiple(id, size);

        for (int i = 0; i < size; i++) {
            AchievementProgress expected = new AchievementProgress(id + i);

            // assert
            assertEquals(sFailMessage, expected.getId(), actual.get(i).getId());
        }
    }
}
