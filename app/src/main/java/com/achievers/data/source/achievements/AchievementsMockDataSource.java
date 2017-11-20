package com.achievers.data.source.achievements;

import com.achievers.data.entities.Achievement;
import com.achievers.data.source._base.AbstractDataSource;
import com.achievers.generator.AchievementsGenerator;

public class AchievementsMockDataSource
        extends AbstractDataSource<Achievement>
        implements AchievementsDataSource {

    private static AchievementsDataSource sINSTANCE;

    public static AchievementsDataSource getInstance() {
        if (sINSTANCE == null) sINSTANCE = new AchievementsMockDataSource();

        return sINSTANCE;
    }

    private AchievementsMockDataSource() {
        super(new AchievementsGenerator());
    }
}