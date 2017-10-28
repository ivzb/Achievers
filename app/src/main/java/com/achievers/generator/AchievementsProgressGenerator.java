package com.achievers.generator;

import com.achievers.data.entities.AchievementProgress;
import com.achievers.utils.GeneratorUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AchievementsProgressGenerator implements BaseGenerator<AchievementProgress> {

    public AchievementProgress single(long id) {
        return GeneratorUtils.getInstance().getAchievementProgress(id, new Date());
    }

    public List<AchievementProgress> multiple(long id, int size) {
        List<AchievementProgress> achievementsProgress = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            AchievementProgress newEntity = single(id + i);
            achievementsProgress.add(newEntity);
        }

        return achievementsProgress;
    }
}