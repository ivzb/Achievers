package com.achievers.data;

import com.achievers.data._base.BaseMockDataSourceTest;
import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsMockDataSource;
import com.achievers.utils.GeneratorUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import io.bloco.faker.Faker;

@RunWith(MockitoJUnitRunner.class)
public class AchievementsMockDataSourceTest extends BaseMockDataSourceTest<Achievement> {

    @Before
    public void before() {
        GeneratorUtils.initialize(new Random(), new Faker());
        mDataSource = AchievementsMockDataSource.getInstance();
    }

    @Test
    public void load_firstPage_assertSuccess() {
        int page = 0;
        int expectedSize = 9;

        load_assertSuccess(null, page, expectedSize);
    }

    @Test
    public void load_thirdPage_assertSuccess() {
        int page = 2;
        int expectedSize = 9;

        load_assertSuccess(null, page, expectedSize);
    }

    @Test(expected = NullPointerException.class)
    public void save_nullCallback_shouldThrow() {
        assertSaveEntityFailure(new Achievement(), null);
    }

    @Test
    public void save_valid_shouldReturnSuccess() {
        assertSaveEntitySuccessful(new Achievement());
    }
}