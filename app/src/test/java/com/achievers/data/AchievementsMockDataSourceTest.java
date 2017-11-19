package com.achievers.data;

import com.achievers.data._base.BaseMockDataSourceTest;
import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsMockDataSource;
import com.achievers.utils.GeneratorUtils;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import io.bloco.faker.Faker;

@RunWith(MockitoJUnitRunner.class)
public class AchievementsMockDataSourceTest extends BaseMockDataSourceTest<Achievement> {

    @Before
    public void before() {
        GeneratorUtils.initialize(new Random(), new Faker());
        super.setDataSource(AchievementsMockDataSource.getInstance());
    }
}