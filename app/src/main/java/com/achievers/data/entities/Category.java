package com.achievers.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.content.ContentValues;
import android.provider.BaseColumns;

import com.achievers.utils.DateConverter;
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
@Entity(tableName = Category.TABLE_NAME)
@TypeConverters({DateConverter.class})
public class Category {

    public static final String TABLE_NAME = "categories";

    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_PARENT_ID = "parent_id";
    public static final String COLUMN_CREATED_ON = "created_on";

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long id;

    @SerializedName("title")
    @ColumnInfo(name = COLUMN_TITLE)
    private String title;

    @SerializedName("description")
    @ColumnInfo(name = COLUMN_DESCRIPTION)
    private String description;

    @SerializedName("imageUrl")
    @ColumnInfo(name = COLUMN_IMAGE_URL)
    private String imageUrl;

    @SerializedName("parent")
    @Ignore
    private Category parent;

    @SerializedName("parentId")
    @ColumnInfo(name = COLUMN_PARENT_ID)
    private Long parentId;

    @SerializedName("createdOn")
    @ColumnInfo(name = COLUMN_CREATED_ON)
    private Date createdOn;

    public Category() { }

    @Ignore
    public Category(long id, Long parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    @Ignore
    public Category(long id, String title, String description,
                    String imageUrl, Long parentId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.parentId = parentId;
    }

    public static Category fromContentValues(ContentValues values) {
        final Category category = new Category();

        if (values.containsKey(COLUMN_ID)) category.setId(values.getAsLong(COLUMN_ID));
        if (values.containsKey(COLUMN_TITLE)) category.setTitle(values.getAsString(COLUMN_TITLE));
        if (values.containsKey(COLUMN_DESCRIPTION)) category.setDescription(values.getAsString(COLUMN_DESCRIPTION));
        if (values.containsKey(COLUMN_IMAGE_URL)) category.setImageUrl(values.getAsString(COLUMN_IMAGE_URL));
        if (values.containsKey(COLUMN_PARENT_ID)) category.setParentId(values.getAsLong(COLUMN_PARENT_ID));
        if (values.containsKey(COLUMN_CREATED_ON)) category.setCreatedOn(DateConverter.fromTimestamp(values.getAsLong(COLUMN_CREATED_ON)));

        return category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

//    public Category getParent() {
//        return parent;
//    }

//    public void setParent(Category parent) {
//        this.parent = parent;
//    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        if (parentId == 0) parentId = null;
        this.parentId = parentId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isNew() {
        return this.getId() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category other = (Category) o;

        if (id != other.id) return false;
        return parentId == other.parentId || (parentId != null && parentId.equals(other.parentId));
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(id: " + this.getId() + ", parent_id: " + this.getParentId() + ")";
    }
}