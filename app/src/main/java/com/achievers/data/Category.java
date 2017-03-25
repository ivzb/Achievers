package com.achievers.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * Immutable model class for a Category.
 */
public final class Category {

    @NonNull
    private final Integer mId;

    @Nullable
    private final String mTitle;

    @Nullable
    private final String mDescription;

    /**
     * Use this constructor to create a new Category.
     *
     * @param title       title of the category
     * @param description description of the category
     */
    public Category(@Nullable String title, @Nullable String description) {
        this(title, description, null);
    }

    /**
     * Use this constructor to specify a Category if the Category already has an id (copy of
     * another Category).
     *
     * @param id          id of the category
     * @param title       title of the category
     * @param description description of the category
     */
    public Category(Integer id, @Nullable String title, @Nullable String description) {
        mId = id;
        mTitle = title;
        mDescription = description;
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

    public boolean isNew() {
        return

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equal(mId, task.mId) &&
                Objects.equal(mTitle, task.mTitle) &&
                Objects.equal(mDescription, task.mDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle, mDescription);
    }

    @Override
    public String toString() {
        return "Task with title " + mTitle;
    }
}