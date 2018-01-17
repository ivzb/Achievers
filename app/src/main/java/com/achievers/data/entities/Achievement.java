package com.achievers.data.entities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.achievers.data.entities._base.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

@Parcel(analyze = { Achievement.class })
public class Achievement implements BaseModel {

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("involvement_id")
    int involvementId;

//    @SerializedName("involvement")
//    Involvement involvement;

    @SerializedName("pictureUri")
    @Deprecated
    Uri pictureUri;

    @SerializedName("picture_url")
    String pictureUrl;

    @SerializedName("user_id")
    String userId;

//    @SerializedName("category")
//    private Category category;

//    @SerializedName("categoryId")
//    private Integer categoryId;

    @SerializedName("created_at")
    Date createdAt;

    @SerializedName("updated_at")
    Date updatedAt;

    @SerializedName("deleted_at")
    Date deletedAt;

    int mInvolvementPosition;

    public Achievement() { }

    public Achievement(String id) {
        this.id = id;
    }

    public Achievement(
            String id,
            @NonNull String title,
            @NonNull String description,
            @NonNull int involvementId,
            @NonNull String pictureUrl,
            @NonNull Date createdAt) {

        this(title, description, involvementId, pictureUrl, createdAt);

        this.id = id;
    }

    public Achievement(
            String id,
            @NonNull String title,
            @NonNull String description,
            @NonNull int involvementId,
            @NonNull String pictureUrl,
            @NonNull String userId,
            @NonNull Date createdAt,
            @NonNull Date updatedAt,
            @NonNull Date deletedAt) {

        this(title, description, involvementId, pictureUrl, createdAt);

        this.id = id;
        this.userId = userId;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Achievement(
            String id,
            @NonNull String title,
            @NonNull String description,
            @NonNull int involvementId,
            @NonNull Uri pictureUri,
            @NonNull Date createdAt) {

        this(title, description, involvementId, "", createdAt);

        this.id = id;
        this.pictureUri = pictureUri;
    }

    public Achievement(
            @NonNull String title,
            @NonNull String description,
            @NonNull int involvementId,
            @NonNull Uri pictureUri) {

        this(title, description, involvementId, "", null);

        this.pictureUri = pictureUri;
    }

    public Achievement(
            @NonNull String title,
            @NonNull String description,
            @NonNull int involvementId,
            @NonNull String pictureUrl,
            Date createdAt) {

        this.title = title;
        this.description = description;
        this.involvementId = involvementId;
        this.pictureUrl = pictureUrl;
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @NonNull
    @Deprecated
    public Uri getPictureUri() {
        return pictureUri;
    }

    public void setPictureUrl(String url) {
        this.pictureUrl = url;
    }

//    @NonNull
//    public Category getCategory() {
//        return category;
//    }

    @Nullable
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getInvolvementPosition() {
        return this.mInvolvementPosition;
    }

    public void setInvolvementPosition(int position) {
        this.mInvolvementPosition = position;
    }

    @Override
    public String getContainerId() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Achievement that = (Achievement) o;

        if (involvementId != that.involvementId) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (pictureUrl != null ? !pictureUrl.equals(that.pictureUrl) : that.pictureUrl != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null)
            return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null)
            return false;
        return deletedAt != null ? deletedAt.equals(that.deletedAt) : that.deletedAt == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + involvementId;
        result = 31 * result + (pictureUrl != null ? pictureUrl.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "id='" + id + '\'' + "\n" +
                ", title='" + title + '\'' + "\n" +
                ", description='" + description + '\'' + "\n" +
                ", involvementId=" + involvementId + "\n" +
                ", pictureUrl='" + pictureUrl + '\'' + "\n" +
                ", userId='" + userId + '\'' + "\n" +
                ", createdAt=" + createdAt + "\n" +
                ", updatedAt=" + updatedAt + "\n" +
                ", deletedAt=" + deletedAt + "\n" +
                '}';
    }

    public enum Type {
        SingleAction(1),
        Progressive(2),
        Meta(3);

        private int mId;

        Type(int id) {
            mId = id;
        }

        public int getId() {
            return mId;
        }
    }
}