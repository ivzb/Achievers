package com.achievers.data.sources.quests;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.data.generators.QuestsGenerator;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources._base.mocks.ReceiveMockDataSource;

import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

public class QuestsMockDataSource
        extends ReceiveMockDataSource<Quest>
        implements QuestsDataSource {

    private static QuestsMockDataSource sINSTANCE;

    public static QuestsMockDataSource getInstance() {
        checkNotNull(sINSTANCE);

        return sINSTANCE;
    }

    public static QuestsMockDataSource createInstance(
            List<Achievement> achievements,
            List<Reward> rewards) {

        sINSTANCE = new QuestsMockDataSource(achievements, rewards);

        return getInstance();
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    private QuestsMockDataSource(
            List<Achievement> achievements,
            List<Reward> rewards) {

        super(new QuestsGenerator(
                GeneratorConfig.getInstance(),
                achievements,
                rewards));
    }
}