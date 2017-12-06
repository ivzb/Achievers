package com.achievers.data.sources.evidences;

import com.achievers.data.entities.Evidence;
import com.achievers.data.generators.EvidencesGenerator;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources._base.mocks.BaseMockDataSource;

import static com.achievers.utils.Preconditions.checkNotNull;

public class EvidencesMockDataSource
        extends BaseMockDataSource<Evidence>
        implements EvidencesDataSource {

    private static EvidencesMockDataSource sINSTANCE;

    public static EvidencesMockDataSource getInstance() {
        checkNotNull(sINSTANCE);

        return sINSTANCE;
    }

    public static EvidencesMockDataSource createInstance() {
        sINSTANCE = new EvidencesMockDataSource();
        return getInstance();
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    private EvidencesMockDataSource() {
        super(new EvidencesGenerator(GeneratorConfig.getInstance()));
    }
}