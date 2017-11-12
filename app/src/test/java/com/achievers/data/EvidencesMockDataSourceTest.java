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

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EvidencesMockDataSourceTest extends BaseMockDataSourceTest<Evidence> {

    @Before
    public void before() {
        GeneratorUtils.initialize(new Random(), new Faker());
        mDataSource = EvidencesMockDataSource.getInstance();
    }

    @Test(expected = NullPointerException.class)
    public void load_nullId_shouldThrow() {
        int page = 1;

        mDataSource.load(null, page, mLoadCallback);
        verify(mLoadCallback).onFailure(mFailureCaptor.capture());

        final String actual = mFailureCaptor.getValue();
        final String expected = "Please provide non negative page.";

        assertEquals(expected, actual);
    }

    @Test
    public void load_firstPage_assertSuccess() {
        long achievementId = 5;
        int page = 0;
        int expectedSize = 9;

        load_assertSuccess(achievementId, page, expectedSize);
    }

    @Test
    public void load_thirdPage_assertSuccess() {
        long achievementId = 5;
        int page = 2;
        int expectedSize = 9;

        load_assertSuccess(achievementId, page, expectedSize);
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