package com.achievers.data.sources.achievements;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.generators.AchievementsGenerator;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources._base.AbstractDataSource;

public class AchievementsMockDataSource
        extends AbstractDataSource<Achievement>
        implements AchievementsDataSource {

    private static AchievementsDataSource sINSTANCE;

    public static AchievementsDataSource getInstance() {
        if (sINSTANCE == null) sINSTANCE = new AchievementsMockDataSource();

        return sINSTANCE;
    }

    private AchievementsMockDataSource() {
        super(new AchievementsGenerator(GeneratorConfig.getInstance()));
    }

    @Override
    public void loadByQuestId(Long id, int page, LoadCallback<Achievement> callback) {
        load(id, page, callback);
    }
}