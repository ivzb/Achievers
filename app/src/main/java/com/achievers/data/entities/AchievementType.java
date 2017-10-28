package com.achievers.data.entities;

public enum AchievementType {
    SingleAction(1),
    Progressive(2),
    Meta(3);

    private int mId;

    AchievementType(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }
}
