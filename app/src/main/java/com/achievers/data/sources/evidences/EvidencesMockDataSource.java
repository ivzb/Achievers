package com.achievers.data.sources.evidences;

import com.achievers.data.entities.Evidence;
import com.achievers.data.generators.EvidencesGenerator;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources._base.AbstractDataSource;

public class EvidencesMockDataSource
        extends AbstractDataSource<Evidence>
        implements EvidencesDataSource {

    private static EvidencesDataSource sINSTANCE;

    public static EvidencesDataSource getInstance() {
        if (sINSTANCE == null) sINSTANCE = new EvidencesMockDataSource();

        return sINSTANCE;
    }

    private EvidencesMockDataSource() {
        super(new EvidencesGenerator(GeneratorConfig.getInstance()));
    }
}