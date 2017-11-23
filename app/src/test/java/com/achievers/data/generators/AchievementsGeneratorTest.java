package com.achievers.data.generators;

import com.achievers.data.entities.Achievement;
import com.achievers.data.generators._base.BaseGenerator;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AchievementsGeneratorTest
        extends AbstractGeneratorTest<Achievement> {

    @Before
    public void before() {
        super.before();

        BaseGenerator<Achievement> generator = new AchievementsGenerator(mGeneratorConfig);
        setGenerator(generator);
    }

    @Override
    public Achievement instantiate(long id) {
        return new Achievement(
                id,
                sWord,
                sSentence,
                sInvolvement,
                sImageUri,
                sDate);
    }
}