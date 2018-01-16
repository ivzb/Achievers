package com.achievers.data.generators;

import android.net.Uri;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Involvement;
import com.achievers.data.generators._base.Generator;
import com.achievers.data.generators._base.contracts.BaseGeneratorConfig;

import java.util.Date;

public class AchievementsGenerator
        extends Generator<Achievement> {

    public AchievementsGenerator(BaseGeneratorConfig config) {
        super(config);
    }

    @Override
    public Achievement instantiate() {
        String id = mConfig.getId();
        String title = mConfig.getWord();
        String description = mConfig.getSentence();
        int involvement_id = mConfig.getEnum(Involvement.values()).getId();
        Uri imageUri = mConfig.getImageUri();
        Date createdOn = mConfig.getDate();

        return new Achievement(
                id,
                title,
                description,
                involvement_id,
                imageUri,
                createdOn);
    }
}