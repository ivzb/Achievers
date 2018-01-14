package com.achievers.data.generators;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Contribution;
import com.achievers.data.generators._base.Generator;
import com.achievers.data.generators._base.contracts.BaseGeneratorConfig;

import java.util.Date;

public class AchievementsProgressGenerator
        extends Generator<Contribution> {

    public AchievementsProgressGenerator(BaseGeneratorConfig config) {
        super(config);
    }

    @Override
    public Contribution instantiate() {
        String id = mConfig.getId();
        String achievementId = mConfig.getId();
        String userId = mConfig.getId();
        Achievement.Type type = mConfig.getEnum(Achievement.Type.values());
        int total = mConfig.getNumber(25);
        int accomplished = mConfig.getNumber(total);
        Date createdOn = mConfig.getDate();

        return new Contribution(
                id,
                achievementId,
                userId,
                type,
                total,
                accomplished,
                createdOn);
    }
}