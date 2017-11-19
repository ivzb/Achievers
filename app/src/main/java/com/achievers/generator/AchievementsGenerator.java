package com.achievers.generator;

import com.achievers.data.entities.Achievement;
import com.achievers.generator._base.BaseGenerator;
import com.achievers.utils.GeneratorUtils;

import java.util.Date;

public class AchievementsGenerator
        extends Generator<Achievement>
        implements BaseGenerator<Achievement> {

    public Achievement single(long id) {
        return GeneratorUtils.getInstance().getAchievement(id, new Date());
    }
}