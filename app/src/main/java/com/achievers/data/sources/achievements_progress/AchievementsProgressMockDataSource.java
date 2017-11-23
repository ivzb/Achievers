package com.achievers.data.sources.achievements_progress;

import com.achievers.data.entities.AchievementProgress;
import com.achievers.data.generators.AchievementsProgressGenerator;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources._base.AbstractDataSource;

public class AchievementsProgressMockDataSource
        extends AbstractDataSource<AchievementProgress>
        implements AchievementsProgressDataSource {

    private static AchievementsProgressDataSource sINSTANCE;

    public static AchievementsProgressDataSource getInstance() {
        if (sINSTANCE == null) sINSTANCE = new AchievementsProgressMockDataSource();

        return sINSTANCE;
    }

    private AchievementsProgressMockDataSource() {
        super(new AchievementsProgressGenerator(GeneratorConfig.getInstance()));
    }
}
