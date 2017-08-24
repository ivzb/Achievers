package com.achievers.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Immutable model class for a Category.
 */
// All classes that extend RealmObject will have a matching RealmProxy class created
// by the annotation processor. Parceler must be made aware of this class. Note that
// the class is not available until the project has been compiled at least once.
@Parcel(
    value = Parcel.Serialization.BEAN,
    analyze = { Category.class })
public class Category {

    @SerializedName("id")
    @NonNull
    private int id;

    @SerializedName("title")
    @NonNull
    private String title;

    @SerializedName("description")
    @NonNull
    private String description;

    @SerializedName("imageUrl")
    @NonNull
    private String imageUrl;

    @SerializedName("parent")
    @NonNull
    private Category parent;

    @SerializedName("parentId")
    @NonNull
    private Integer parentId;

    @SerializedName("createdOn")
    @Nullable
    private Date createdOn;

    public Category() { }

    public Category(int id, String title, String description,
                    String imageUrl, Integer parentId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.parentId = parentId;
    }

    public int getId() {
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

    @Nullable
    public Category getParent() {
        return parent;
    }

    @Nullable
    public Integer getParentId() {
        return parentId;
    }

    @Nullable
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public boolean isNew() {
        return this.getId() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category other = (Category) o;

        return this.getId() == other.getId() && this.getParentId() == other.getParentId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, title, description);
    }

    @Override
    public String toString() {
        return "Category #" + this.getId() + " with title: " + this.getTitle() + " and description: " + this.getDescription();
    }
}