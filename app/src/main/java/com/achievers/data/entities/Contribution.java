package com.achievers.data.entities;

import com.achievers.data.entities._base.BaseModel;

import java.util.Date;

public class Contribution implements BaseModel {

    private String mId;
    private String mAchievementId;
    private String mUserId;
    private Achievement.Type mType;
    private int mTotal;
    private int mAccomplished;
    private Date mCreatedAt;

    public Contribution() {

    }

    public Contribution(String id) {
        mId = id;
    }

    public Contribution(
        String id,
        String achievementId,
        String userId,
        Achievement.Type type,
        int total,
        int accomplished,
        Date createdAt) {

        this(id);
        mAchievementId = achievementId;
        mUserId = userId;
        mType = type;
        mTotal = total;
        mAccomplished = accomplished;
        mCreatedAt = createdAt;
    }

    public String getId() {
        return mId;
    }

    @Override
    public void setId(String id) {
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
    public Date getCreatedAt() {
        return mCreatedAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
    }

    @Override
    public String getContainerId() {
        return mAchievementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contribution that = (Contribution) o;

        if (mTotal != that.mTotal) return false;
        if (mAccomplished != that.mAccomplished) return false;
        if (mId != null ? !mId.equals(that.mId) : that.mId != null) return false;
        if (mAchievementId != null ? !mAchievementId.equals(that.mAchievementId) : that.mAchievementId != null)
            return false;
        if (mUserId != null ? !mUserId.equals(that.mUserId) : that.mUserId != null) return false;
        if (mType != that.mType) return false;
        return mCreatedAt != null ? mCreatedAt.equals(that.mCreatedAt) : that.mCreatedAt == null;
    }

    @Override
    public int hashCode() {
        int result = mId != null ? mId.hashCode() : 0;
        result = 31 * result + (mAchievementId != null ? mAchievementId.hashCode() : 0);
        result = 31 * result + (mUserId != null ? mUserId.hashCode() : 0);
        result = 31 * result + (mType != null ? mType.hashCode() : 0);
        result = 31 * result + mTotal;
        result = 31 * result + mAccomplished;
        result = 31 * result + (mCreatedAt != null ? mCreatedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Contribution{" +
                "mId=" + mId +
                ", mAchievementId=" + mAchievementId +
                ", mUserId=" + mUserId +
                ", mType=" + mType +
                ", mTotal=" + mTotal +
                ", mAccomplished=" + mAccomplished +
                ", mCreatedAt=" + mCreatedAt +
                '}';
    }
}
