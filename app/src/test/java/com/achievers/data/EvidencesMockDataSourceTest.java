package com.achievers.data;

import com.achievers.data._base.BaseMockDataSourceTest;
import com.achievers.data.entities.Evidence;
import com.achievers.data.source.evidences.EvidencesMockDataSource;
import com.achievers.utils.GeneratorUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import io.bloco.faker.Faker;

@RunWith(MockitoJUnitRunner.class)
public class EvidencesMockDataSourceTest extends BaseMockDataSourceTest<Evidence> {

    @Before
    public void before() {
        GeneratorUtils.initialize(new Random(), new Faker());
        super.setDataSource(EvidencesMockDataSource.getInstance());
    }

    @Test(expected = NullPointerException.class)
    public void save_nullCallback_shouldThrow() {
        assertSaveEntityFailure(new Evidence(), null);
    }

    @Test
    public void save_valid_shouldReturnSuccess() {
        assertSaveEntitySuccessful(new Evidence());
    }
}