package com.achievers;

import com.achievers.data.sources.DataSources;
import com.achievers.data.sources.MockStrategy;
import com.facebook.stetho.Stetho;

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

//        GeneratorConfig.initialize(new Random(), new Faker());
//        Seed.initialize(GeneratorConfig.getInstance());
        DataSources.createInstance(new MockStrategy());
    }
}