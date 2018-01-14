package com.achievers.data.sources;

import com.achievers.data._base.ReceiveDataSourceTest;
import com.achievers.data.entities.Reward;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources.rewards.RewardsMockDataSource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

@RunWith(MockitoJUnitRunner.class)
public class RewardsMockDataSourceTest extends ReceiveDataSourceTest<Reward> {

    private RewardsMockDataSource mDataSource;

    @Override
    public List<Reward> seed(String containerId, int size) {
        return mDataSource.seed(containerId, size);
    }

    @Before
    public void before() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(new Random(), new Faker());

        RewardsMockDataSource.destroyInstance();
        mDataSource = RewardsMockDataSource.createInstance();

        super.setDataSource(mDataSource);
    }
}