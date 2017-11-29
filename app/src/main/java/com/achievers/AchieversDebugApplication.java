package com.achievers;

import com.achievers.data.generators.config.GeneratorConfig;
import com.facebook.stetho.Stetho;

import java.util.Random;

import io.bloco.faker.Faker;

public class AchieversDebugApplication extends AchieversApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (!isUnitTesting()) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build()
            );
        }

        GeneratorConfig.initialize(new Random(), new Faker());
    }
}