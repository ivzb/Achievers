package com.achievers.data.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.achievers.data.entities._base.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

@Parcel(analyze = { Evidence.class })
public class Evidence implements BaseModel {

    @SerializedName("id")
    @NonNull
    long id;

    @SerializedName("comment")
    @NonNull
    String comment;

    @SerializedName("evidenceType")
    @NonNull
    EvidenceType evidenceType;

    @SerializedName("achievementId")
    long achievementId;

    @SerializedName("previewUrl")
    @NonNull
    String previewUrl;

    @SerializedName("url")
    @NonNull
    String url;

    @SerializedName("createdOn")
    @Nullable
    Date createdOn;

    public Evidence() {

    }

    public Evidence(long id) {
        this.id = id;
    }

    public Evidence(long id, @NonNull String comment, @NonNull EvidenceType evidenceType,
                    @NonNull String previewUrl, @NonNull String url, @Nullable Date createdOn) {
        this(id);

        this.comment = comment;
        this.evidenceType = evidenceType;
        this.previewUrl = previewUrl;
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
    public String getPreviewUrl() {
        return previewUrl;
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
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + comment.hashCode();
        result = 31 * result + evidenceType.hashCode();
        result = 31 * result + (int) (achievementId ^ (achievementId >>> 32));
        result = 31 * result + url.hashCode();
        result = 31 * result + previewUrl.hashCode();
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Evidence #" + this.getId() + " with comment: " + this.getComment();
    }
}