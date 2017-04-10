package com.achievers.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Involvement {
    Bronze(1),
    Silver(2),
    Gold(3),
    Platinum(4),
    Diamond(5);

    private int id;

    Involvement(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private static final List<Involvement> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Involvement getRandomInvolvement() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}