package com.achievers.data.sources;

import com.achievers.data._base.ReceiveDataSourceTest;
import com.achievers.data.entities.AchievementProgress;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources.achievements_progress.AchievementsProgressMockDataSource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import io.bloco.faker.Faker;

@RunWith(MockitoJUnitRunner.class)
public class AchievementsProgressMockDataSourceTest
        extends ReceiveDataSourceTest<AchievementProgress> {

    private AchievementsProgressMockDataSource mDataSource;

    @Override
    public void seed(Long containerId, int size) {
        mDataSource.seed(containerId, size);
    }

    @Before
    public void before() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(new Random(), new Faker());

        AchievementsProgressMockDataSource.destroyInstance();
        mDataSource = AchievementsProgressMockDataSource.createInstance();

        super.setDataSource(mDataSource);
    }
}
