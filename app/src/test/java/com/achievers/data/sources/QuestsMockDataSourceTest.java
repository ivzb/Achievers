package com.achievers.data.sources;

import com.achievers.data._base.ReceiveDataSourceTest;
import com.achievers.data.entities.Quest;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources.quests.QuestsMockDataSource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import io.bloco.faker.Faker;

@RunWith(MockitoJUnitRunner.class)
public class QuestsMockDataSourceTest extends ReceiveDataSourceTest<Quest> {

    @Before
    public void before() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(new Random(), new Faker());
        super.setDataSource(QuestsMockDataSource.getInstance());
    }
}