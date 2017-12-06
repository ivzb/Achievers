package com.achievers;

import android.app.Application;

import com.achievers.utils.SharedPreferencesUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

public class AchieversApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DefaultConfig.initialize(getResources());
        SharedPreferencesUtils.initialize(this);

        if (isUnitTesting()) {
            Fresco.initialize(this);
            return;
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        ImagePipelineConfig imagePipelineConfig = OkHttpImagePipelineConfigFactory
                .newBuilder(this, okHttpClient)
                .setDownsampleEnabled(true)
                .build();

        Fresco.initialize(this, imagePipelineConfig);
    }

    protected boolean isUnitTesting() {
        return false;
    }
}