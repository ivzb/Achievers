package com.achievers.data.entities;

import android.net.Uri;

import com.achievers.data.entities._base.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

@Parcel(analyze = { Quest.class })
public class Quest implements BaseModel {

    @SerializedName("id")
    long id;

    @SerializedName("name")
    String name;

    @SerializedName("pictureUri")
    Uri pictureUri;

    @SerializedName("achievementsCount")
    int achievementsCount;

    @SerializedName("createdOn")
    Date createdOn;

    public Quest() {

    }

    public Quest(long id) {
        this.id = id;
    }

    public Quest(
            long id,
            String name,
            Uri pictureUri,
            int achievementsCount,
            Date createdOn) {

        this(id);
        this.name = name;
        this.pictureUri = pictureUri;
        this.achievementsCount = achievementsCount;
        this.createdOn = createdOn;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Uri getPictureUri() {
        return pictureUri;
    }

    public int getAchievementsCount() {
        return achievementsCount;
    }

    @Override
    public Date getCreatedOn() {
        return createdOn;
    }

    @Override
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public Long getContainerId() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quest quest = (Quest) o;

        if (id != quest.id) return false;
        if (achievementsCount != quest.achievementsCount) return false;
        if (name != null ? !name.equals(quest.name) : quest.name != null) return false;
        if (pictureUri != null ? !pictureUri.equals(quest.pictureUri) : quest.pictureUri != null)
            return false;
        return createdOn != null ? createdOn.equals(quest.createdOn) : quest.createdOn == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pictureUri != null ? pictureUri.hashCode() : 0);
        result = 31 * result + achievementsCount;
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", achievementsCount=" + achievementsCount +
                ", startedOn=" + createdOn +
                '}';
    }
}