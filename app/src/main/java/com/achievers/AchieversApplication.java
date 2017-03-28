package com.achievers;

import android.app.Application;
import com.facebook.stetho.Stetho;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AchieversApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
            Stetho
                .newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        );

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.deleteRealm(realmConfiguration); // Clear the realm from last time
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}