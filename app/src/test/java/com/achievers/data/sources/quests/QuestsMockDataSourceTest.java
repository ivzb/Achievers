package com.achievers.data.sources.quests;

import com.achievers.data._base.ReceiveDataSourceTest;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.data.generators.config.GeneratorConfig;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

@RunWith(MockitoJUnitRunner.class)
public class QuestsMockDataSourceTest extends ReceiveDataSourceTest<Quest> {

    private QuestsMockDataSource mDataSource;

    @Override
    public List<Quest> seed(String containerId, int size) {
        return mDataSource.seed(containerId, size);
    }

    @Before
    public void before() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(new Random(), new Faker());

        List<Achievement> achievements = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            achievements.add(new Achievement(String.valueOf(i)));
        }

        List<Reward> rewards = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            rewards.add(new Reward(String.valueOf(i)));
        }

        QuestsMockDataSource.destroyInstance();
        mDataSource = QuestsMockDataSource.createInstance(achievements, rewards);

        super.setDataSource(mDataSource);
    }
}