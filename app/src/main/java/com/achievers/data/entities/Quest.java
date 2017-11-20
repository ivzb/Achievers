package com.achievers.data.entities;

import com.achievers.data.entities._base.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Arrays;
import java.util.Date;

@Parcel(analyze = { Quest.class })
public class Quest implements BaseModel {

    @SerializedName("id")
    long id;

    @SerializedName("name")
    String name;

    @SerializedName("achievementIds")
    long[] achievementIds;

    @SerializedName("createdOn")
    Date createdOn;

    public Quest() {

    }

    public Quest(long id) {
        this.id = id;
    }

    public Quest(long id, String name, long[] achievementIds, Date createdOn) {
        this(id);
        this.name = name;
        this.achievementIds = achievementIds;
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

    public long[] getAchievementIds() {
        return achievementIds;
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
        if (!name.equals(quest.name)) return false;
        if (!Arrays.equals(achievementIds, quest.achievementIds)) return false;
        return createdOn.equals(quest.createdOn);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + Arrays.hashCode(achievementIds);
        result = 31 * result + createdOn.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", achievementIds=" + Arrays.toString(achievementIds) +
                ", startedOn=" + createdOn +
                '}';
    }
}