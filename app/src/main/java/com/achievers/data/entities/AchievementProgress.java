package com.achievers.data.entities;

import com.achievers.data.entities._base.BaseModel;

import java.util.Date;

public class AchievementProgress implements BaseModel {

    private long mId;
    private long mAchievementId;
    private long mUserId;
    private Achievement.Type mType;
    private int mTotal;
    private int mAccomplished;
    private Date mCreatedOn;

    public AchievementProgress(long id) {
        mId = id;
    }

    public AchievementProgress(
        long id,
        long achievementId,
        long userId,
        Achievement.Type type,
        int total,
        int accomplished,
        Date createdOn) {

        this(id);
        mAchievementId = achievementId;
        mUserId = userId;
        mType = type;
        mTotal = total;
        mAccomplished = accomplished;
        mCreatedOn = createdOn;
    }

    public long getId() {
        return mId;
    }

    @Override
    public void setId(long id) {
        mId = id;
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

    @Override
    public Date getCreatedOn() {
        return mCreatedOn;
    }

    @Override
    public void setCreatedOn(Date createdOn) {
        mCreatedOn = createdOn;
    }

    @Override
    public Long getContainerId() {
        return mAchievementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AchievementProgress that = (AchievementProgress) o;

        if (mId != that.mId) return false;
        if (mAchievementId != that.mAchievementId) return false;
        if (mUserId != that.mUserId) return false;
        if (mTotal != that.mTotal) return false;
        if (mAccomplished != that.mAccomplished) return false;
        if (mType != that.mType) return false;
        return mCreatedOn != null ? mCreatedOn.equals(that.mCreatedOn) : that.mCreatedOn == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (mId ^ (mId >>> 32));
        result = 31 * result + (int) (mAchievementId ^ (mAchievementId >>> 32));
        result = 31 * result + (int) (mUserId ^ (mUserId >>> 32));
        result = 31 * result + (mType != null ? mType.hashCode() : 0);
        result = 31 * result + mTotal;
        result = 31 * result + mAccomplished;
        result = 31 * result + (mCreatedOn != null ? mCreatedOn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AchievementProgress{" +
                "mId=" + mId +
                ", mAchievementId=" + mAchievementId +
                ", mUserId=" + mUserId +
                ", mType=" + mType +
                ", mTotal=" + mTotal +
                ", mAccomplished=" + mAccomplished +
                ", mCreatedOn=" + mCreatedOn +
                '}';
    }
}
