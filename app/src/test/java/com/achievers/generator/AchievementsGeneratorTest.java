package com.achievers.generator;

import com.achievers.data.entities.Achievement;
import com.achievers.generator._base.BaseGenerator;
import com.achievers.utils.GeneratorUtils;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

import static junit.framework.Assert.assertEquals;

public class AchievementsGeneratorTest {

    private static String sFailMessage = "Generated achievement does not match";

    private BaseGenerator<Achievement> mGenerator;

    @Before
    public void before() {
        GeneratorUtils.initialize(new Random(), new Faker());
        mGenerator = new AchievementsGenerator();
    }

    @Test
    public void testGeneratingSingleAchievement() {
        long id = 5;
        Achievement expected = new Achievement(id);

        Achievement actual = mGenerator.single(id);

        assertEquals(sFailMessage, expected.getId(), actual.getId());
    }

    @Test
    public void testGeneratingMultipleAchievement() {
        long id = 5;
        int size = 5;

        List<Achievement> actual = mGenerator.multiple(id, size);

        for (int i = 0; i < size; i++) {
            Achievement expected = new Achievement(id + i);
            assertEquals(sFailMessage, expected.getId(), actual.get(i).getId());
        }
    }
}
