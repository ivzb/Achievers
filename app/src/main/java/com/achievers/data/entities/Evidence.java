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
    String id;

    @SerializedName("comment")
    @NonNull
    String comment;

    @SerializedName("multimediaType")
    @NonNull
    MultimediaType multimediaType;

    @SerializedName("achievementId")
    String achievementId;

//    @SerializedName("ownerId")
//    long ownerId;

    @SerializedName("preview_url")
    @NonNull
    String previewUrl;

    @SerializedName("uri")
    @NonNull
    @Deprecated
    Uri uri;

    @SerializedName("url")
    String url;

    @SerializedName("created_at")
    @Nullable
    Date createdAt;

    public Evidence() {

    }

    public Evidence(String id) {
        this.id = id;
    }

    public Evidence(
            String comment,
            String achievementId,
//            long ownerId,
            MultimediaType multimediaType,
            Uri multimediaUri) {

        this.comment = comment;
        this.achievementId = achievementId;
//        this.ownerId = ownerId;
        this.multimediaType = multimediaType;
        this.uri = multimediaUri;
    }

    public Evidence(
            String id,
            @NonNull String comment,
            @NonNull MultimediaType multimediaType,
            @NonNull String previewUrl,
            @NonNull Uri uri,
            @Nullable Date createdAt) {

        this(id);

        this.comment = comment;
        this.multimediaType = multimediaType;
        this.previewUrl = previewUrl;
        this.uri = uri;
        this.createdAt = createdAt;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(String achievementId) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @NonNull
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NonNull Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isNew() {
        return this.getId() == null || this.getId().equals("");
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public String getContainerId() {
        return achievementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Evidence evidence = (Evidence) o;

        if (!id.equals(evidence.id)) return false;
        if (!comment.equals(evidence.comment)) return false;
        if (multimediaType != evidence.multimediaType) return false;
        if (achievementId != null ? !achievementId.equals(evidence.achievementId) : evidence.achievementId != null)
            return false;
        if (!previewUrl.equals(evidence.previewUrl)) return false;
        if (uri != null ? !uri.equals(evidence.uri) : evidence.uri != null) return false;
        return createdAt != null ? createdAt.equals(evidence.createdAt) : evidence.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + comment.hashCode();
        result = 31 * result + multimediaType.hashCode();
        result = 31 * result + (achievementId != null ? achievementId.hashCode() : 0);
        result = 31 * result + previewUrl.hashCode();
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Evidence #" + this.getId() + " with comment: " + this.getComment();
    }
}