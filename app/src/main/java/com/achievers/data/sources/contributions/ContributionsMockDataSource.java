package com.achievers.data.sources.contributions;

import com.achievers.data.entities.Contribution;
import com.achievers.data.generators.AchievementsProgressGenerator;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources._base.mocks.BaseMockDataSource;

import static com.achievers.utils.Preconditions.checkNotNull;

public class ContributionsMockDataSource
        extends BaseMockDataSource<Contribution>
        implements ContributionsDataSource {

    private static ContributionsMockDataSource sINSTANCE;

    public static ContributionsMockDataSource getInstance() {
        return checkNotNull(sINSTANCE);
    }

    public static ContributionsMockDataSource createInstance() {
        sINSTANCE = new ContributionsMockDataSource();
        return getInstance();
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    private ContributionsMockDataSource() {
        super(new AchievementsProgressGenerator(GeneratorConfig.getInstance()));
    }
}
