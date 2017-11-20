package com.achievers.data.source.achievements_progress;

import com.achievers.data.entities.AchievementProgress;
import com.achievers.data.source._base.AbstractDataSource;
import com.achievers.generator.AchievementsProgressGenerator;

public class AchievementsProgressMockDataSource
        extends AbstractDataSource<AchievementProgress>
        implements AchievementsProgressDataSource {

    private static AchievementsProgressDataSource sINSTANCE;

    public static AchievementsProgressDataSource getInstance() {
        if (sINSTANCE == null) sINSTANCE = new AchievementsProgressMockDataSource();

        return sINSTANCE;
    }

    private AchievementsProgressMockDataSource() {
        super(new AchievementsProgressGenerator());
    }
}
