package com.achievers.data.sources;

import com.achievers.data._base.ReceiveDataSourceTest;
import com.achievers.data.entities.Contribution;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources.contributions.ContributionsMockDataSource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import io.bloco.faker.Faker;

@RunWith(MockitoJUnitRunner.class)
public class AchievementsProgressMockDataSourceTest
        extends ReceiveDataSourceTest<Contribution> {

    private ContributionsMockDataSource mDataSource;

    @Override
    public void seed(Long containerId, int size) {
        mDataSource.seed(containerId, size);
    }

    @Before
    public void before() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(new Random(), new Faker());

        ContributionsMockDataSource.destroyInstance();
        mDataSource = ContributionsMockDataSource.createInstance();

        super.setDataSource(mDataSource);
    }
}
