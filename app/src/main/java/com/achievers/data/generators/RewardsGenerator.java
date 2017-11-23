package com.achievers.data.generators;

import com.achievers.data.entities.Reward;
import com.achievers.data.generators._base.BaseGeneratorConfig;

import java.util.Date;

public class RewardsGenerator
        extends AbstractGenerator<Reward> {

    public RewardsGenerator(BaseGeneratorConfig config) {
        super(config);
    }

    @Override
    public Reward instantiate(long id) {
        String name = mConfig.getWord();
        Reward.Type type = mConfig.getEnum(Reward.Type.values());
        Date createdOn = mConfig.getDate();

        return new Reward(
                id,
                name,
                type,
                createdOn);
    }
}