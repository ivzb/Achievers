package com.achievers.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Immutable model class for a Achievement.
 */
public class Achievement extends RealmObject {

    @SerializedName("Id")
    @NonNull
    @PrimaryKey
    private Integer id;

    @SerializedName("Title")
    @NonNull
    private String title;

    @SerializedName("Description")
    @NonNull
    private String description;

    @SerializedName("ImageUrl")
    @NonNull
    private String imageUrl;

    @SerializedName("Category")
    @NonNull
    private Category category;

    @SerializedName("Involvement")
    @NonNull
    private Involvement involvement;

    @SerializedName("CreatedOn")
    @NonNull
    private Date createdOn;

    /**
     * An empty constructor is required by realm.
     */
    public Achievement() { }

    /**
     * Use this constructor to specify a Achievement if the Achievement already has an id
     *
     * @param id          id of the achievement
     * @param title       title of the achievement
     * @param description description of the achievement
     * @param imageUrl    image url of the achievement
     * @param category    category the achievement
     * @param involvement involvement of the achievement
     * @param createdOn   creation date of the achievement
     */
    public Achievement(Integer id, @NonNull String title, @NonNull String description, @NonNull String imageUrl,
                    @NonNull Category category, @NonNull Involvement involvement, @Nullable Date createdOn) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.involvement = involvement;
        this.createdOn = createdOn;
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
        return involvement;
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

        Category other = (Category) o;

        return this.getId() == other.getId() &&
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