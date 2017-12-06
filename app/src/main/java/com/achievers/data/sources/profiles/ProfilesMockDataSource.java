package com.achievers.data.sources.profiles;


import com.achievers.data.entities.Profile;
import com.achievers.data.generators.ProfilesGenerator;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources._base.mocks.GetMockDataSource;

public class ProfilesMockDataSource
        extends GetMockDataSource<Profile>
        implements ProfilesDataSource {

    private static ProfilesMockDataSource sINSTANCE;

    public static ProfilesMockDataSource getInstance() {
        return sINSTANCE;
    }

    public static ProfilesMockDataSource createInstance() {
        sINSTANCE = new ProfilesMockDataSource();
        return getInstance();
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    private ProfilesMockDataSource() {
        super(new ProfilesGenerator(
                GeneratorConfig.getInstance()));
    }
}