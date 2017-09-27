package com.achievers;

import com.achievers.utils.GeneratorUtils;
import com.facebook.stetho.Stetho;

import java.util.Random;

import io.bloco.faker.Faker;

public class AchieversDebugApplication extends AchieversApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        );

        GeneratorUtils.initialize(new Random(), new Faker());
    }
}