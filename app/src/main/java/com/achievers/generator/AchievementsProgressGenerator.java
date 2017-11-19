package com.achievers.generator;

import com.achievers.data.entities.AchievementProgress;
import com.achievers.generator._base.BaseGenerator;
import com.achievers.utils.GeneratorUtils;

import java.util.Date;

public class AchievementsProgressGenerator
        extends Generator<AchievementProgress>
        implements BaseGenerator<AchievementProgress> {

    public AchievementProgress single(long id) {
        return GeneratorUtils.getInstance().getAchievementProgress(id, new Date());
    }
}