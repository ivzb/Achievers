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
    long id;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("pictureUrl")
    String pictureUrl;

//    @SerializedName("category")
//    private Category category;

//    @SerializedName("categoryId")
//    private Integer categoryId;

    @SerializedName("involvement")
    Involvement involvement;

    @SerializedName("createdOn")
    Date createdOn;

    private Uri mPictureUri;
    private int mInvolvementPosition;

    public Achievement() { }

    public Achievement(long id) {
        this.id = id;
    }

    public Achievement(
            long id,
            @NonNull String title,
            @NonNull String description,
            @NonNull Involvement involvement,
            @NonNull Uri pictureUri) {

        this(title, description, involvement, pictureUri);

        this.id = id;
    }
    public Achievement(
            long id,
            @NonNull String title,
            @NonNull String description,
            @NonNull Involvement involvement,
            @NonNull String pictureUrl) {

        this(title, description, involvement, pictureUrl);

        this.id = id;
    }

    public Achievement(
            @NonNull String title,
            @NonNull String description,
            @NonNull Involvement involvement,
            @NonNull Uri pictureUri) {

        this.title = title;
        this.description = description;
        this.involvement = involvement;
        this.mPictureUri = pictureUri;
    }

    public Achievement(
            @NonNull String title,
            @NonNull String description,
            @NonNull Involvement involvement,
            @NonNull String pictureUrl) {

        this.title = title;
        this.description = description;
        this.involvement = involvement;
        this.pictureUrl = pictureUrl;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
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
    public Date getCreatedOn() {
        return createdOn;
    }

    public Uri getPictureUri() {
        return mPictureUri;
    }

    public void setPictureUri(Uri pictureUri) {
        this.mPictureUri = pictureUri;
    }

    public int getInvolvementPosition() {
        return this.mInvolvementPosition;
    }

    public void setInvolvementPosition(int position) {
        this.mInvolvementPosition = position;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Achievement that = (Achievement) o;

        if (id != that.id) return false;
        if (!title.equals(that.title)) return false;
        if (!description.equals(that.description)) return false;
        if (!pictureUrl.equals(that.pictureUrl)) return false;
        if (involvement != that.involvement) return false;
        return true;//createdOn.equals(that.createdOn);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + pictureUrl.hashCode();
        result = 31 * result + involvement.hashCode();
        result = 31 * result + createdOn.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Achievement #" + this.getId() + " with title: " + this.getTitle() + " and description: " + this.getDescription();
    }
}