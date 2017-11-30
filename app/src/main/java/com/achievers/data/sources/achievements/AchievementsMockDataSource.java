package com.achievers.data.sources.achievements;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.generators.AchievementsGenerator;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources._base.mocks.BaseMockDataSource;

public class AchievementsMockDataSource
        extends BaseMockDataSource<Achievement>
        implements AchievementsDataSource {

    private static AchievementsMockDataSource sINSTANCE;

    public static AchievementsMockDataSource getInstance() {
        return sINSTANCE;
    }

    public static AchievementsMockDataSource createInstance() {
        sINSTANCE = new AchievementsMockDataSource();
        return getInstance();
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    private AchievementsMockDataSource() {
        super(new AchievementsGenerator(GeneratorConfig.getInstance()));
    }

    @Override
    public void loadByQuestId(final Long questId, int page, final LoadCallback<Achievement> callback) {
        load(questId, page, callback);
    }
}