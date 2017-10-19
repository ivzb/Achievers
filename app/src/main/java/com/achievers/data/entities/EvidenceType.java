package com.achievers.data.entities;

import com.achievers.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum EvidenceType {
    Photo(1, 0),
    Video(2, R.drawable.ic_play),
    Audio(3, R.drawable.ic_music_note);

    private int mId;
    private int mDrawable;

    EvidenceType(int id, int drawable) {
        mId = id;
        mDrawable = drawable;
    }

    public int getId() {
        return mId;
    }

    public int getDrawable() {
        return mDrawable;
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