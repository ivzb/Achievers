package com.achievers.data;

import com.achievers.data._base.ReceiveDataSourceTest;
import com.achievers.data.entities.Quest;
import com.achievers.data.source.quests.QuestsMockDataSource;
import com.achievers.utils.GeneratorUtils;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import io.bloco.faker.Faker;

@RunWith(MockitoJUnitRunner.class)
public class QuestsMockDataSourceTest extends ReceiveDataSourceTest<Quest> {

    @Before
    public void before() {
        GeneratorUtils.initialize(new Random(), new Faker());
        super.setDataSource(QuestsMockDataSource.getInstance());
    }
}