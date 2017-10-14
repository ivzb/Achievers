package com.achievers.generator;

import com.achievers.data.entities.Evidence;
import com.achievers.utils.GeneratorUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EvidencesGenerator implements BaseGenerator<Evidence> {

    public Evidence single(long id) {
        return GeneratorUtils.getInstance().getEvidence(id, new Date());
    }

    public List<Evidence> multiple(long id, int size) {
        List<Evidence> evidences = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            Evidence newEvidence = single(id + i);
            evidences.add(newEvidence);
        }

        return evidences;
    }
}