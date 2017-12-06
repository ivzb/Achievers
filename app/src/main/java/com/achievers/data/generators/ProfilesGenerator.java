package com.achievers.data.generators;

import android.net.Uri;

import com.achievers.data.entities.Profile;
import com.achievers.data.generators._base.Generator;
import com.achievers.data.generators._base.contracts.BaseGeneratorConfig;

import java.util.Date;

public class ProfilesGenerator
        extends Generator<Profile> {

    public ProfilesGenerator(BaseGeneratorConfig config) {
        super(config);
    }

    @Override
    public Profile instantiate(long id) {
        String name = mConfig.getWord();
        Uri picture = mConfig.getImageUri();
        Date createdOn = mConfig.getDate();

        return new Profile(
                id,
                name,
                picture,
                createdOn);
    }
}