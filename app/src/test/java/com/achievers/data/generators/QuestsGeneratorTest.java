package com.achievers.data.generators;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.data.generators._base.BaseGenerator;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.TreeSet;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class QuestsGeneratorTest
        extends AbstractGeneratorTest<Quest> {

    private @Mock BaseGenerator<Achievement> mAchievementsGenerator;
    private @Mock BaseGenerator<Reward> mRewardsGenerator;
    private @Mock List<Achievement> mAchievements;
    private @Mock TreeSet<Long> mCompleted;
    private @Mock List<Reward> mRewards;

    @Before
    public void before() {
        super.before();

        when(mGeneratorConfig.getIdsAmong(isA(List.class))).thenReturn(mCompleted);

        when(mAchievementsGenerator.multiple(anyLong(), anyInt())).thenReturn(mAchievements);
        when(mRewardsGenerator.multiple(anyLong(), anyInt())).thenReturn(mRewards);

        BaseGenerator<Quest> generator = new QuestsGenerator(
                mGeneratorConfig,
                mAchievementsGenerator,
                mRewardsGenerator);

        setGenerator(generator);
    }

    @Override
    public Quest instantiate(long id) {
        return new Quest(
                id,
                sWord,
                sImageUri,
                sInvolvement,
                sNumber,
                mAchievements,
                mCompleted,
                mRewards,
                sQuestType,
                sDate);
    }
}
