package com.achievers;

import android.app.Application;

import com.achievers.util.CustomToroPlayStrategy;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import im.ene.toro.Toro;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;

public class AchieversApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .name("Achievers")
                .build();
//        Realm.deleteRealm(realmConfiguration); // Clear the realm from last time
        Realm.setDefaultConfiguration(realmConfiguration);

        Toro.init(this);
        Toro.setStrategy(new CustomToroPlayStrategy());

        Fresco.initialize(this);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        ImagePipelineConfig imagePipelineConfig = OkHttpImagePipelineConfigFactory
                .newBuilder(this, okHttpClient)
                .build();

        Fresco.initialize(this, imagePipelineConfig);
    }
}