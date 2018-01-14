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

    @SerializedName("pictureUri")
    Uri pictureUri;

//    @SerializedName("category")
//    private Category category;

//    @SerializedName("categoryId")
//    private Integer categoryId;

    @SerializedName("involvement")
    Involvement involvement;

    @SerializedName("created_at")
    Date createdAt;

    int mInvolvementPosition;

    public Achievement() { }

    public Achievement(String id) {
        this.id = id;
    }

    public Achievement(
            String id,
            @NonNull String title,
            @NonNull String description,
            @NonNull Involvement involvement,
            @NonNull Uri pictureUri,
            @NonNull Date createdAt) {

        this(title, description, involvement, pictureUri, createdAt);

        this.id = id;
    }

    public Achievement(
            @NonNull String title,
            @NonNull String description,
            @NonNull Involvement involvement,
            @NonNull Uri pictureUri) {

        this(title, description, involvement, pictureUri, null);
    }

    public Achievement(
            @NonNull String title,
            @NonNull String description,
            @NonNull Involvement involvement,
            @NonNull Uri pictureUri,
            Date createdAt) {

        this.title = title;
        this.description = description;
        this.involvement = involvement;
        this.pictureUri = pictureUri;
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
    public Uri getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(Uri pictureUri) {
        this.pictureUri = pictureUri;
    }

//    @NonNull
//    public Category getCategory() {
//        return category;
//    }

    @NonNull
    public Involvement getInvolvement() {
        return involvement;
    }

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

        if (mInvolvementPosition != that.mInvolvementPosition) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (pictureUri != null ? !pictureUri.equals(that.pictureUri) : that.pictureUri != null)
            return false;
        if (involvement != that.involvement) return false;
        return createdAt != null ? createdAt.equals(that.createdAt) : that.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (pictureUri != null ? pictureUri.hashCode() : 0);
        result = 31 * result + (involvement != null ? involvement.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + mInvolvementPosition;
        return result;
    }

    @Override
    public String toString() {
        return "Achievement #" + this.getId() + " with title: " + this.getTitle() + " and description: " + this.getDescription();
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