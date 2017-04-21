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
 * Immutable model class for an Evidence.
 */
public class Evidence extends RealmObject {

    @SerializedName("Id")
    @NonNull
    @PrimaryKey
    private Integer id;

    @SerializedName("Comment")
    @NonNull
    private String comment;

    @SerializedName("Type")
    @NonNull
    private int type;

    @SerializedName("Data")
    @NonNull
    private String data;

    @SerializedName("Achievement")
    @NonNull
    private Achievement achievement;

    @SerializedName("CreatedOn")
    @NonNull
    private Date createdOn;

    /**
     * An empty constructor is required by realm.
     */
    public Evidence() { }

    /**
     * Use this constructor to specify an Evidence if the Evidence already has an id
     *
     * @param id          id of the evidence
     * @param comment     comment of the evidence
     * @param type        type of the evidence
     * @param data        data url of the evidence
     * @param achievement achievement of the evidence
     * @param createdOn   creation date of the evidence
     */
    public Evidence(Integer id, @NonNull String comment, @NonNull int type,
                    @NonNull String data, @NonNull Achievement achievement, @Nullable Date createdOn) {
        this.id = id;
        this.comment = comment;
        this.type = type;
        this.data = data;
        this.achievement = achievement;
        this.createdOn = createdOn;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getComment() {
        return comment;
    }

    public void setComment(@NonNull String comment) {
        this.comment = comment;
    }

    @NonNull
    public int getType() {
        return type;
    }

    public void setType(@NonNull int type) {
        this.type = type;
    }

    @NonNull
    public String getData() {
        return data;
    }

    public void setData(@NonNull String data) {
        this.data = data;
    }

    @NonNull
    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(@NonNull Achievement achievement) {
        this.achievement = achievement;
    }

    @NonNull
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(@NonNull Date createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isNew() {
        return this.getId() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Evidence other = (Evidence) o;

        return this.getId() == other.getId() &&
                this.getComment().equals(other.getComment());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, comment);
    }

    @Override
    public String toString() {
        return "Evidence #" + this.getId() + " with comment: " + this.getComment();
    }
}