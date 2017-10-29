package com.achievers.data.entities;

import com.achievers.data.entities._base.BaseModel;

public class AchievementProgress implements BaseModel {

    private long mId;
    private long mAchievementId;
    private long mUserId;
    private AchievementType mType;
    private int mTotal;
    private int mAccomplished;

    public AchievementProgress(
        long id,
        long achievementId,
        long userId,
        AchievementType type,
        int total,
        int accomplished) {

        mId = id;
        mAchievementId = achievementId;
        mUserId = userId;
        mType = type;
        mTotal = total;
        mAccomplished = accomplished;
    }

    public long getId() {
        return mId;
    }

    public int getProgress() {
        if (mTotal == 0) return 0;
        return (int)((float)mAccomplished / mTotal * 100);
    }

    public int getTotal() {
        return mTotal;
    }

    public int getAccomplished() {
        return mAccomplished;
    }
}
