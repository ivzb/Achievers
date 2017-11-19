package com.achievers.generator;

import com.achievers.data.entities.Quest;
import com.achievers.generator._base.BaseGenerator;
import com.achievers.utils.GeneratorUtils;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

import static junit.framework.Assert.assertEquals;

public class QuestsGeneratorTest {

    private static String sFailMessage = "Generated quest does not match";

    private BaseGenerator<Quest> mGenerator;

    @Before
    public void before() {
        GeneratorUtils.initialize(new Random(), new Faker());
        mGenerator = new QuestsGenerator();
    }

    @Test
    public void testGeneratingSingleQuest() {
        long id = 5;
        Quest expected = new Quest(id);

        Quest actual = mGenerator.single(id);

        assertEquals(sFailMessage, expected.getId(), actual.getId());
    }

    @Test
    public void testGeneratingMultipleEvidence() {
        long id = 5;
        int size = 5;

        List<Quest> actual = mGenerator.multiple(id, size);

        for (int i = 0; i < size; i++) {
            Quest expected = new Quest(id + i);
            assertEquals(sFailMessage, expected.getId(), actual.get(i).getId());
        }
    }
}
