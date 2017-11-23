package com.achievers.data.sources.quests;

import com.achievers.data.entities.Quest;
import com.achievers.data.generators.QuestsGenerator;
import com.achievers.data.generators.RewardsGenerator;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources._base.AbstractReceiveDataSource;

public class QuestsMockDataSource
        extends AbstractReceiveDataSource<Quest>
        implements QuestsDataSource {

    private static QuestsDataSource sINSTANCE;

    public static QuestsDataSource getInstance() {
        if (sINSTANCE == null) sINSTANCE = new QuestsMockDataSource();

        return sINSTANCE;
    }

    private QuestsMockDataSource() {
        super(
            new QuestsGenerator(GeneratorConfig.getInstance(),
                new RewardsGenerator(GeneratorConfig.getInstance())));
    }
}