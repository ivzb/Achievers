package com.achievers.data.sources;

import com.achievers.data._base.ReceiveDataSourceTest;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources.quests.QuestsMockDataSource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

@RunWith(MockitoJUnitRunner.class)
public class QuestsMockDataSourceTest extends ReceiveDataSourceTest<Quest> {

    private QuestsMockDataSource mDataSource;

    private @Mock List<Achievement> mAchievements;
    private @Mock List<Reward> mRewards;

    @Override
    public void seed(Long containerId, int size) {
        mDataSource.seed(containerId, size);
    }

    @Before
    public void before() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(new Random(), new Faker());

        QuestsMockDataSource.destroyInstance();
        mDataSource = QuestsMockDataSource.createInstance(mAchievements, mRewards);

        super.setDataSource(mDataSource);
    }
}