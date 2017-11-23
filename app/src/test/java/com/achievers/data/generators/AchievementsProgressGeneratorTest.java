package com.achievers.data.generators;

import com.achievers.data.entities.AchievementProgress;
import com.achievers.data.generators._base.BaseGenerator;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AchievementsProgressGeneratorTest
        extends AbstractGeneratorTest<AchievementProgress> {

    @Before
    public void before() {
        super.before();

        BaseGenerator<AchievementProgress> generator = new AchievementsProgressGenerator(mGeneratorConfig);
        setGenerator(generator);
    }

    @Override
    public AchievementProgress instantiate(long id) {
        return new AchievementProgress(
                id,
                sId,
                sId,
                sAchievementType,
                sNumber,
                sNumber,
                sDate);
    }
}