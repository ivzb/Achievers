package com.achievers.data.sources;

import com.achievers.data._base.ReceiveDataSourceTest;
import com.achievers.data.entities.AchievementProgress;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources.achievements_progress.AchievementsProgressMockDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import io.bloco.faker.Faker;

@RunWith(MockitoJUnitRunner.class)
public class AchievementsProgressMockDataSourceTest
        extends ReceiveDataSourceTest<AchievementProgress> {

    @Before
    public void before() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(new Random(), new Faker());
        super.setDataSource(AchievementsProgressMockDataSource.getInstance());
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
}
