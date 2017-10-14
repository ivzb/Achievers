package com.achievers.generator;

import com.achievers.data.entities.Evidence;
import com.achievers.utils.GeneratorUtils;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

import static junit.framework.Assert.assertEquals;

public class EvidencesGeneratorTest {

    private static String sFailMessage = "Generated evidence does not match";

    private BaseGenerator<Evidence> mGenerator;

    @Before
    public void before() {
        GeneratorUtils.initialize(new Random(), new Faker());
        mGenerator = new EvidencesGenerator();
    }

    @Test
    public void testGeneratingSingleEvidence() {
        long id = 5;
        Evidence expected = new Evidence(id);

        Evidence actual = mGenerator.single(id);

        assertEquals(sFailMessage, expected.getId(), actual.getId());
    }

    @Test
    public void testGeneratingMultipleEvidence() {
        long id = 5;
        int size = 5;

        List<Evidence> actual = mGenerator.multiple(id, size);

        for (int i = 0; i < size; i++) {
            Evidence expected = new Evidence(id + i);
            assertEquals(sFailMessage, expected.getId(), actual.get(i).getId());
        }
    }
}
