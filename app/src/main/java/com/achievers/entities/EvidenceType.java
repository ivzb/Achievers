package com.achievers.entities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum EvidenceType {
    Image(1),
    Video(2),
    Voice(3),
    Location(4);

    private int id;

    EvidenceType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private static final List<EvidenceType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static EvidenceType getRandomEvidenceType() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static EvidenceType getById(int id) {
        return EvidenceType.values()[id - 1];
    }
}