package com.achievers;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

public class DebugAchieversApplication extends AchieversApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        );
    }
}