package com.achievers.data.sources.contributions;

import com.achievers.data._base.ReceiveDataSourceTest;
import com.achievers.data.entities.Contribution;
import com.achievers.data.generators.config.GeneratorConfig;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

@RunWith(MockitoJUnitRunner.class)
public class ContributionsMockDataSourceTest
        extends ReceiveDataSourceTest<Contribution> {

    private ContributionsMockDataSource mDataSource;

    @Override
    public List<Contribution> seed(String containerId, int size) {
        return mDataSource.seed(containerId, size);
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
