package com.achievers.data.generators;

import com.achievers.data.entities.Reward;
import com.achievers.data.generators._base.contracts.BaseGenerator;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RewardsGeneratorTest
        extends AbstractGeneratorTest<Reward> {

    @Before
    public void before() {
        super.before();

        BaseGenerator<Reward> generator = new RewardsGenerator(mGeneratorConfig);
        setGenerator(generator);
    }

    @Override
    public Reward instantiate(String id) {
        return new Reward(
                id,
                sWord,
                sSentence,
                sImageUri,
                sRewardType,
                sDate);
    }
}
