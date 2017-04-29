package com.achievers.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
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

    @SerializedName("EvidenceType")
    @NonNull
    private int evidenceType;

    @SerializedName("Url")
    @NonNull
    private String url;

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
     * @param id           id of the evidence
     * @param comment      comment of the evidence
     * @param evidenceType type of the evidence
     * @param url         url url of the evidence
     * @param achievement  achievement of the evidence
     * @param createdOn    creation date of the evidence
     */
    public Evidence(Integer id, @NonNull String comment, @NonNull EvidenceType evidenceType,
                    @NonNull String url, @NonNull Achievement achievement, @Nullable Date createdOn) {
        this.id = id;
        this.comment = comment;
        this.evidenceType = evidenceType.getId();
        this.url = url;
        this.achievement = achievement;
        this.createdOn = createdOn;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public String getComment() {
        return comment;
    }

    @NonNull
    public EvidenceType getEvidenceType() {
        return EvidenceType.values()[evidenceType - 1];
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    @NonNull
    public Achievement getAchievement() {
        return achievement;
    }

    @NonNull
    public Date getCreatedOn() {
        return createdOn;
    }

    public boolean isNew() {
        return this.getId() == 0;
    }

    public void setUrl(String url) {
        this.url = url;
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