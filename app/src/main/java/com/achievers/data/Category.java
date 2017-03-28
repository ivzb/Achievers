package com.achievers.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Immutable model class for a Category.
 */
public class Category extends RealmObject {

    @SerializedName("Id")
    @NonNull
    private Integer mId;

    @SerializedName("Title")
    @Nullable
    private String mTitle;

    @SerializedName("Description")
    @Nullable
    private String mDescription;

    @SerializedName("CreatedOn")
    @Nullable
    private Date mCreatedOn;

    /**
     * An empty constructor is required by realm.
     */
    public Category() { }

    /**
     * Use this constructor to create a new Category.
     *
     * @param title       title of the category
     * @param description description of the category
     */
    public Category(@NonNull String title, @NonNull String description) {
        this(0, title, description, null);
    }

    /**
     * Use this constructor to specify a Category if the Category already has an id (copy of
     * another Category).
     *
     * @param id          id of the category
     * @param title       title of the category
     * @param description description of the category
     */
    public Category(Integer id, @NonNull String title, @NonNull String description, @Nullable Date createdOn) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mCreatedOn = createdOn;
    }

    public Integer getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getTitleForList() {
        if (!Strings.isNullOrEmpty(mTitle)) {
            return mTitle;
        } else {
            return mDescription;
        }
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    @Nullable
    public Date getCreatedOn() {
        return mCreatedOn;
    }

    public boolean isNew() {
        return this.getId() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category other = (Category) o;

        return Objects.equal(this.getId(), other.getId()) &&
                Objects.equal(this.getTitle(), other.getTitle()) &&
                Objects.equal(this.getDescription(), other.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle, mDescription);
    }

    @Override
    public String toString() {
        return "Category #" + this.getId() + " with title: " + this.getTitle() + " and description: " + this.getDescription();
    }
}