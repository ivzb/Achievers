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

        DataSources.createInstance(new MockStrategy());
    }
}