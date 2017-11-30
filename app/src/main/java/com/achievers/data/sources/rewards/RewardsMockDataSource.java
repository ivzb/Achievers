package com.achievers.data.sources.rewards;

import com.achievers.data.entities.Reward;
import com.achievers.data.generators.RewardsGenerator;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources._base.mocks.ReceiveMockDataSource;

public class RewardsMockDataSource
        extends ReceiveMockDataSource<Reward>
        implements RewardsDataSource {

    private static RewardsMockDataSource sINSTANCE;

    public static RewardsMockDataSource getInstance() {
        return sINSTANCE;
    }

    public static RewardsMockDataSource createInstance() {
        sINSTANCE = new RewardsMockDataSource();
        return getInstance();
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    private RewardsMockDataSource() {
        super(new RewardsGenerator(GeneratorConfig.getInstance()));
    }
}