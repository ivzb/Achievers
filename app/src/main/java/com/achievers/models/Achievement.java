package com.achievers.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Immutable model class for a Achievement.
 */
public class Achievement {

    @SerializedName("id")
    @NonNull
    private Integer id;

    @SerializedName("title")
    @NonNull
    private String title;

    @SerializedName("description")
    @NonNull
    private String description;

    @SerializedName("imageUrl")
    @NonNull
    private String imageUrl;

    @SerializedName("category")
    @NonNull
    private Category category;

    @SerializedName("categoryId")
    @NonNull
    private Integer categoryId;

    @SerializedName("involvement")
    @NonNull
    private String involvement;

    @SerializedName("createdOn")
    @NonNull
    private Date createdOn;

    /**
     * An empty constructor is required by realm.
     */
    public Achievement() { }

    /**
     * Use this constructor to specify a Achievement if the Achievement already has an id
     *
     * @param title       title of the achievement
     * @param description description of the achievement
     * @param imageUrl    image url of the achievement
     * @param categoryId    category the achievement
     * @param involvement involvement of the achievement
     */
    public Achievement(@NonNull String title, @NonNull String description, @NonNull String imageUrl,
                    @NonNull Integer categoryId, @NonNull String involvement) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.involvement = involvement;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getTitleForList() {
        if (!Strings.isNullOrEmpty(title)) {
            return title;
        } else {
            return description;
        }
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    @NonNull
    public Category getCategory() {
        return category;
    }

    @NonNull
    public Involvement getInvolvement() {
        return Involvement.valueOf(involvement);
    }

    @Nullable
    public Date getCreatedOn() {
        return createdOn;
    }

    public boolean isNew() {
        return this.getId() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Achievement other = (Achievement) o;

        return this.getId().equals(other.getId()) &&
                this.getTitle().equals(other.getTitle()) &&
                this.getDescription().equals(other.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, title, description);
    }

    @Override
    public String toString() {
        return "Achievement #" + this.getId() + " with title: " + this.getTitle() + " and description: " + this.getDescription();
    }
}