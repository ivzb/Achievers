package com.achievers.data.sources;

import com.achievers.data._base.GetDataSourceTest;
import com.achievers.data.entities.Profile;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources.profiles.ProfilesMockDataSource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

@RunWith(MockitoJUnitRunner.class)
public class ProfilesMockDataSourceTest extends GetDataSourceTest<Profile> {

    private ProfilesMockDataSource mDataSource;

    @Override
    public List<Profile> seed(String containerId, int size) {
        return mDataSource.seed(containerId, size);
    }

    @Before
    public void before() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(new Random(), new Faker());

        ProfilesMockDataSource.destroyInstance();
        mDataSource = ProfilesMockDataSource.createInstance();

        super.setDataSource(mDataSource);
    }
}