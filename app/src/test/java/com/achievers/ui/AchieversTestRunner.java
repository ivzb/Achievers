package com.achievers.ui;

import com.achievers.ui.add_achievement.FileProviderShadow;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.internal.bytecode.InstrumentationConfiguration;
import org.robolectric.internal.bytecode.ShadowMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AchieversTestRunner extends RobolectricTestRunner {

    public AchieversTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    public InstrumentationConfiguration createClassLoaderConfig() {
        InstrumentationConfiguration.Builder builder = InstrumentationConfiguration.newBuilder();
        builder.addInstrumentedPackage("com.achievers");
        return builder.build();
    }
}