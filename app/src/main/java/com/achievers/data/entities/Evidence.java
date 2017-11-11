package com.achievers.data.entities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.utils.ui.multimedia.MultimediaType;
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

    @SerializedName("multimediaType")
    @NonNull
    MultimediaType multimediaType;

    @SerializedName("achievementId")
    long achievementId;

    @SerializedName("previewUrl")
    @NonNull
    String previewUrl;

    @SerializedName("uri")
    @NonNull
    Uri uri;

    @SerializedName("createdOn")
    @Nullable
    Date createdOn;

    public Evidence() {

    }

    public Evidence(long id) {
        this.id = id;
    }

    public Evidence(
            String comment,
            long achievementId,
            MultimediaType multimediaType,
            Uri multimediaUri) {

        this.comment = comment;
        this.achievementId = achievementId;
        this.multimediaType = multimediaType;
        this.uri = multimediaUri;
    }

    public Evidence(
            long id,
            @NonNull String comment,
            @NonNull MultimediaType multimediaType,
            @NonNull String previewUrl,
            @NonNull Uri uri,
            @Nullable Date createdOn) {

        this(id);

        this.comment = comment;
        this.multimediaType = multimediaType;
        this.previewUrl = previewUrl;
        this.uri = uri;
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
    public MultimediaType getMultimediaType() {
        return multimediaType;
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
    public Uri getUri() {
        return uri;
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

    public void setUri(Uri uri) {
        this.uri = uri;
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
        result = 31 * result + multimediaType.hashCode();
        result = 31 * result + (int) (achievementId ^ (achievementId >>> 32));
        result = 31 * result + uri.hashCode();
        result = 31 * result + previewUrl.hashCode();
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Evidence #" + this.getId() + " with comment: " + this.getComment();
    }
}