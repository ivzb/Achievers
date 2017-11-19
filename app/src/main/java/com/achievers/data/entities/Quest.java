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

    @SerializedName("startedOn")
    Date startedOn;

    public Quest() {

    }

    public Quest(long id) {
        this.id = id;
    }

    public Quest(long id, String name, long[] achievementIds, Date startedOn) {
        this(id);
        this.name = name;
        this.achievementIds = achievementIds;
        this.startedOn = startedOn;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long[] getAchievementIds() {
        return achievementIds;
    }

    public Date getStartedOn() {
        return startedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quest quest = (Quest) o;

        if (id != quest.id) return false;
        if (!name.equals(quest.name)) return false;
        if (!Arrays.equals(achievementIds, quest.achievementIds)) return false;
        return startedOn.equals(quest.startedOn);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + Arrays.hashCode(achievementIds);
        result = 31 * result + startedOn.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", achievementIds=" + Arrays.toString(achievementIds) +
                ", startedOn=" + startedOn +
                '}';
    }
}