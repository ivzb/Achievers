package com.achievers.data.generators;

import android.net.Uri;

import com.achievers.data.entities.Involvement;
import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.data.generators._base.BaseGenerator;
import com.achievers.data.generators._base.BaseGeneratorConfig;

import java.util.Date;
import java.util.List;

public class QuestsGenerator
        extends AbstractGenerator<Quest> {

    private BaseGenerator<Reward> mRewardsGenerator;

    public QuestsGenerator(BaseGeneratorConfig config, BaseGenerator<Reward> rewardsGenerator) {
        super(config);

        mRewardsGenerator = rewardsGenerator;
    }

    @Override
    public Quest instantiate(long id) {
        String name = mConfig.getWord();
        Uri picture = mConfig.getImageUri();
        int achievementsCount = mConfig.getNumber(15);
        Involvement involvement = mConfig.getEnum(Involvement.values());
        int requiredLevel = mConfig.getNumber(20);

        int rewardsSize = mConfig.getNumber(7);
        List<Reward> rewards = mRewardsGenerator.multiple(0, rewardsSize);

        Quest.Type type = mConfig.getEnum(Quest.Type.values());
        Date createdOn = mConfig.getDate();

        return new Quest(
                id,
                name,
                picture,
                achievementsCount,
                involvement,
                requiredLevel,
                rewards,
                type,
                createdOn);
    }
}