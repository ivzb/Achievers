package com.achievers.data.generators;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.AchievementProgress;
import com.achievers.data.generators._base.Generator;
import com.achievers.data.generators._base.contracts.BaseGeneratorConfig;

import java.util.Date;

public class AchievementsProgressGenerator
        extends Generator<AchievementProgress> {

    public AchievementsProgressGenerator(BaseGeneratorConfig config) {
        super(config);
    }

    @Override
    public AchievementProgress instantiate(long id) {
        long achievementId = mConfig.getId();
        long userId = mConfig.getId();
        Achievement.Type type = mConfig.getEnum(Achievement.Type.values());
        int total = mConfig.getNumber(25);
        int accomplished = mConfig.getNumber(total);
        Date createdOn = mConfig.getDate();

        return new AchievementProgress(
                id,
                achievementId,
                userId,
                type,
                total,
                accomplished,
                createdOn);
    }
}