package com.achievers.data.entities;

import com.achievers.data.entities._base.BaseModel;

public class AchievementProgress implements BaseModel {

    private long mId;
    private long mAchievementId;
    private long mUserId;
    private AchievementType mType;
    private int mProgress;

    public AchievementProgress(
        long id,
        long achievementId,
        long userId,
        AchievementType type,
        int progress) {

        mId = id;
        mAchievementId = achievementId;
        mUserId = userId;
        mType = type;
        mProgress = progress;
    }

    public long getId() {
        return mId;
    }
}
