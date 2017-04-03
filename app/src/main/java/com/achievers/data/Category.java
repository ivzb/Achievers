package com.achievers.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Immutable model class for a Category.
 */
public class Category extends RealmObject {

    @SerializedName("Id")
    @NonNull
    @PrimaryKey
    private Integer id;

    @SerializedName("Title")
    @Nullable
    private String title;

    @SerializedName("Description")
    @Nullable
    private String description;

    @SerializedName("Parent")
    @Nullable
    private Category parent;

    @SerializedName("Children")
    @Nullable
    private RealmList<Category> children;

    @SerializedName("CreatedOn")
    @Nullable
    private Date createdOn;

    /**
     * An empty constructor is required by realm.
     */
    public Category() { }

    /**
     * Use this constructor to specify a Category if the Category already has an id (copy of
     * another Category).
     *
     * @param id          id of the category
     * @param title       title of the category
     * @param description description of the category
     */
    public Category(Integer id, @NonNull String title, @NonNull String description,
                    @Nullable Date createdOn) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.children = new RealmList<>();
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

    @Nullable
    public Category getParent() {
        return parent;
    }

    @Nullable
    public RealmList<Category> getChildren() {
        return children;
    }

    @Nullable
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void setChildren(RealmList<Category> children) {
        this.children = children;
    }

    public void addChild(Category child) {
        this.children.add(child);
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
        return "Category #" + this.getId() + " with title: " + this.getTitle() + " and description: " + this.getDescription();
    }
}