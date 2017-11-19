package com.achievers.generator;

import com.achievers.data.entities.Evidence;
import com.achievers.generator._base.BaseGenerator;
import com.achievers.utils.GeneratorUtils;

import java.util.Date;

public class EvidencesGenerator
        extends Generator<Evidence>
        implements BaseGenerator<Evidence> {

    public Evidence single(long id) {
        return GeneratorUtils.getInstance().getEvidence(id, new Date());
    }
}