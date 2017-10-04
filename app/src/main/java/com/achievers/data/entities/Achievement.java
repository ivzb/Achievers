package com.achievers.data.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

@Parcel(analyze = { Achievement.class })
public class Achievement {

    @SerializedName("id")
    long id;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("imageUrl")
    String imageUrl;

//    @SerializedName("category")
//    private Category category;

//    @SerializedName("categoryId")
//    private Integer categoryId;

    @SerializedName("involvement")
    Involvement involvement;

    @SerializedName("createdOn")
    Date createdOn;

    public Achievement() { }

    public Achievement(long id) {
        this.id = id;
    }

    public Achievement(
            long id,
            @NonNull String title,
            @NonNull String description,
            @NonNull String imageUrl,
            @NonNull Involvement involvement,
            @NonNull Date createdOn) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.involvement = involvement;
        this.createdOn = createdOn;
    }

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
    public String getImageUrl() {
        return imageUrl;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Achievement that = (Achievement) o;

        if (id != that.id) return false;
        if (!title.equals(that.title)) return false;
        if (!description.equals(that.description)) return false;
        if (!imageUrl.equals(that.imageUrl)) return false;
        if (involvement != that.involvement) return false;
        return createdOn.equals(that.createdOn);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + imageUrl.hashCode();
        result = 31 * result + involvement.hashCode();
        result = 31 * result + createdOn.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Achievement #" + this.getId() + " with title: " + this.getTitle() + " and description: " + this.getDescription();
    }
}