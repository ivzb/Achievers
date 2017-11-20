package com.achievers.data.source.evidences;

import com.achievers.data.entities.Evidence;
import com.achievers.data.source._base.AbstractDataSource;
import com.achievers.generator.EvidencesGenerator;

public class EvidencesMockDataSource
        extends AbstractDataSource<Evidence>
        implements EvidencesDataSource {

    private static EvidencesDataSource sINSTANCE;

    public static EvidencesDataSource getInstance() {
        if (sINSTANCE == null) sINSTANCE = new EvidencesMockDataSource();

        return sINSTANCE;
    }

    private EvidencesMockDataSource() {
        super(new EvidencesGenerator());
    }
}