package com.achievers.data.generators;

import android.net.Uri;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Involvement;
import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.data.generators._base.Generator;
import com.achievers.data.generators._base.contracts.BaseGeneratorConfig;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;

public class QuestsGenerator
        extends Generator<Quest> {

    private List<Achievement> mAchievements;
    private List<Reward> mRewards;

    public QuestsGenerator(
            BaseGeneratorConfig config,
            List<Achievement> achievements,
            List<Reward> rewards) {

        super(config);

        mAchievements = achievements;
        mRewards = rewards;
    }

    @Override
    public Quest instantiate(long id) {
        String name = mConfig.getWord();
        Uri picture = mConfig.getImageUri();
        Involvement involvement = mConfig.getEnum(Involvement.values());
        int experience = mConfig.getNumber(2000);

        int achievementsSize = mConfig.getNumber(mAchievements.size());
        List<Achievement> achievements = mConfig.getAmong(mAchievements, achievementsSize);
        TreeSet<Long> completed = mConfig.getIdsAmong(achievements, mConfig.getNumber(achievements.size()));

        int rewardsSize = mConfig.getNumber(mRewards.size());
        List<Reward> rewards = mConfig.getAmong(mRewards, rewardsSize);

        Quest.Type type = mConfig.getEnum(Quest.Type.values());
        Date createdOn = mConfig.getDate();

        return new Quest(
                id,
                name,
                picture,
                involvement,
                experience,
                achievements,
                completed,
                rewards,
                type,
                createdOn);
    }
}