package com.achievers.data.source.quests;

import com.achievers.data.entities.Quest;
import com.achievers.data.source._base.AbstractReceiveDataSource;
import com.achievers.generator.QuestsGenerator;

public class QuestsMockDataSource
        extends AbstractReceiveDataSource<Quest>
        implements QuestsDataSource {

    private static QuestsDataSource sINSTANCE;

    public static QuestsDataSource getInstance() {
        if (sINSTANCE == null) sINSTANCE = new QuestsMockDataSource();

        return sINSTANCE;
    }

    private QuestsMockDataSource() {
        super(new QuestsGenerator());
    }
}