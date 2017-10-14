package com.achievers.data.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Evidence {

    @SerializedName("id")
    @NonNull
    private long id;

    @SerializedName("comment")
    @NonNull
    private String comment;

    @SerializedName("evidenceType")
    @NonNull
    private EvidenceType evidenceType;

    @SerializedName("achievementId")
    private long achievementId;

    @SerializedName("url")
    @NonNull
    private String url;

    @SerializedName("createdOn")
    @Nullable
    private Date createdOn;

    public Evidence() {

    }

    public Evidence(long id) {
        this.id = id;
    }

    public Evidence(long id, @NonNull String comment, @NonNull EvidenceType evidenceType,
                    @NonNull String url,  @Nullable Date createdOn) {
        this(id);

        this.comment = comment;
        this.evidenceType = evidenceType;
        this.url = url;
        this.createdOn = createdOn;
    }

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getComment() {
        return comment;
    }

    @NonNull
    public EvidenceType getEvidenceType() {
        return evidenceType;
    }

    public long getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(long achievementId) {
        this.achievementId = achievementId;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    @NonNull
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(@NonNull Date createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isNew() {
        return this.getId() == 0;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Evidence other = (Evidence) o;

        return this.getId() == other.getId() && this.getComment().equals(other.getComment());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, comment);
    }

    @Override
    public String toString() {
        return "Evidence #" + this.getId() + " with comment: " + this.getComment();
    }
}