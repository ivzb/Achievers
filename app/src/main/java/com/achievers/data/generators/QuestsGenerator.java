package com.achievers.data.generators;

import android.net.Uri;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Involvement;
import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.data.generators._base.BaseGenerator;
import com.achievers.data.generators._base.BaseGeneratorConfig;

import java.util.Date;
import java.util.List;

public class QuestsGenerator
        extends AbstractGenerator<Quest> {

    private BaseGenerator<Achievement> mAchievementsGenerator;
    private BaseGenerator<Reward> mRewardsGenerator;

    public QuestsGenerator(
            BaseGeneratorConfig config,
            BaseGenerator<Achievement> achievementsGenerator,
            BaseGenerator<Reward> rewardsGenerator) {

        super(config);

        mAchievementsGenerator = achievementsGenerator;
        mRewardsGenerator = rewardsGenerator;
    }

    @Override
    public Quest instantiate(long id) {
        String name = mConfig.getWord();
        Uri picture = mConfig.getImageUri();
        Involvement involvement = mConfig.getEnum(Involvement.values());
        int requiredLevel = mConfig.getNumber(20);
        int experience = mConfig.getNumber(2000);

        int achievementsSize = mConfig.getNumber(15);
        List<Achievement> achievements = mAchievementsGenerator.multiple(0, achievementsSize);

        List<Achievement> completedAchievements = mAchievementsGenerator.getAmong(achievements);

        int rewardsSize = mConfig.getNumber(7);
        List<Reward> rewards = mRewardsGenerator.multiple(0, rewardsSize);

        Quest.Type type = mConfig.getEnum(Quest.Type.values());
        Date createdOn = mConfig.getDate();

        return new Quest(
                id,
                name,
                picture,
                involvement,
                experience,
                requiredLevel,
                achievements,
                completedAchievements,
                rewards,
                type,
                createdOn);
    }
}