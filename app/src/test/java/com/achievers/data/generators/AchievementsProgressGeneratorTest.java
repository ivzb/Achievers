package com.achievers.data.generators;

import com.achievers.data.entities.Contribution;
import com.achievers.data.generators._base.contracts.BaseGenerator;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AchievementsProgressGeneratorTest
        extends AbstractGeneratorTest<Contribution> {

    @Before
    public void before() {
        super.before();

        BaseGenerator<Contribution> generator = new AchievementsProgressGenerator(mGeneratorConfig);
        setGenerator(generator);
    }

    @Override
    public Contribution instantiate(String id) {
        return new Contribution(
                id,
                sId,
                sId,
                sAchievementType,
                sNumber,
                sNumber,
                sDate);
    }
}