package com.achievers.data.generators;

import android.net.Uri;

import com.achievers.data.entities.Reward;
import com.achievers.data.generators._base.Generator;
import com.achievers.data.generators._base.contracts.BaseGeneratorConfig;

import java.util.Date;

public class RewardsGenerator
        extends Generator<Reward> {

    public RewardsGenerator(BaseGeneratorConfig config) {
        super(config);
    }

    @Override
    public Reward instantiate(long id) {
        String name = mConfig.getWord();
        String description = mConfig.getSentence();
        Uri pictureUri = mConfig.getImageUri();
        Reward.Type type = mConfig.getEnum(Reward.Type.values());
        Date createdOn = mConfig.getDate();

        return new Reward(
                id,
                name,
                description,
                pictureUri,
                type,
                createdOn);
    }
}