package com.achievers.generator;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Involvement;
import com.achievers.utils.GeneratorUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

public class AchievementsGenerator {

    public Achievement single(long id) {
        return GeneratorUtils.getInstance().getAchievement(id, new Date());
    }

    public List<Achievement> multiple(long id, int size) {
        List<Achievement> achievements = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            Achievement achievement = single(id + i);
            achievements.add(achievement);
        }

        return achievements;
    }
}