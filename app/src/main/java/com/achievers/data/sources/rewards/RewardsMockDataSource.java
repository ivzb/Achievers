package com.achievers.data.sources.rewards;

import com.achievers.data.entities.Reward;
import com.achievers.data.generators.RewardsGenerator;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources._base.AbstractReceiveDataSource;

public class RewardsMockDataSource
        extends AbstractReceiveDataSource<Reward>
        implements RewardsDataSource {

    private static RewardsDataSource sINSTANCE;

    public static RewardsDataSource getInstance() {
        if (sINSTANCE == null) sINSTANCE = new RewardsMockDataSource();

        return sINSTANCE;
    }

    private RewardsMockDataSource() {
        super(new RewardsGenerator(GeneratorConfig.getInstance()));
    }
}