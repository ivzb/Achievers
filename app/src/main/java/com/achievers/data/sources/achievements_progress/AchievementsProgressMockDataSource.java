package com.achievers.data.sources.achievements_progress;

import com.achievers.data.entities.AchievementProgress;
import com.achievers.data.generators.AchievementsProgressGenerator;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources._base.mocks.BaseMockDataSource;

import static com.achievers.utils.Preconditions.checkNotNull;

public class AchievementsProgressMockDataSource
        extends BaseMockDataSource<AchievementProgress>
        implements AchievementsProgressDataSource {

    private static AchievementsProgressMockDataSource sINSTANCE;

    public static AchievementsProgressMockDataSource getInstance() {
        checkNotNull(sINSTANCE);

        return sINSTANCE;
    }

    public static AchievementsProgressMockDataSource createInstance() {
        sINSTANCE = new AchievementsProgressMockDataSource();
        return getInstance();
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    private AchievementsProgressMockDataSource() {
        super(new AchievementsProgressGenerator(GeneratorConfig.getInstance()));
    }
}
