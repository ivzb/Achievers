package com.achievers.data.sources;

import com.achievers.data._base.BaseMockDataSourceTest;
import com.achievers.data.entities.Achievement;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources.achievements.AchievementsMockDataSource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

@RunWith(MockitoJUnitRunner.class)
public class AchievementsMockDataSourceTest extends BaseMockDataSourceTest<Achievement> {

    private AchievementsMockDataSource mDataSource;

    @Override
    public List<Achievement> seed(String containerId, int size) {
        return mDataSource.seed(containerId, size);
    }

    @Before
    public void before() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(new Random(), new Faker());

        AchievementsMockDataSource.destroyInstance();
        mDataSource = AchievementsMockDataSource.createInstance();

        super.setDataSource(mDataSource);
    }
}