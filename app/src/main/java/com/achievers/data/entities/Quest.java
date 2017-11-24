package com.achievers.data.entities;

import android.net.Uri;

import com.achievers.data.entities._base.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @SerializedName("involvement")
    Involvement involvement;

    @SerializedName("requiredLevel")
    int requiredLevel;

    @SerializedName("rewards")
    List<Reward> rewards;

    @SerializedName("type")
    Quest.Type type;

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
            Involvement involvement,
            int requiredLevel,
            List<Reward> rewards,
            Quest.Type type,
            Date createdOn) {

        this(id);
        this.name = name;
        this.pictureUri = pictureUri;
        this.achievementsCount = achievementsCount;
        this.involvement = involvement;
        this.requiredLevel = requiredLevel;
        this.rewards = rewards;
        this.type = type;
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

    public Involvement getInvolvement() {
        return involvement;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public Type getType() {
        return type;
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

    public String getDescription() {
        return String.format(Locale.getDefault(), "%d level, %d achievements, %s",
                requiredLevel,
                achievementsCount,
                type.name());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quest quest = (Quest) o;

        if (id != quest.id) return false;
        if (achievementsCount != quest.achievementsCount) return false;
        if (requiredLevel != quest.requiredLevel) return false;
        if (name != null ? !name.equals(quest.name) : quest.name != null) return false;
        if (pictureUri != null ? !pictureUri.equals(quest.pictureUri) : quest.pictureUri != null)
            return false;
        if (involvement != quest.involvement) return false;
        if (rewards != null ? !rewards.equals(quest.rewards) : quest.rewards != null) return false;
        if (type != quest.type) return false;
        return createdOn != null ? createdOn.equals(quest.createdOn) : quest.createdOn == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pictureUri != null ? pictureUri.hashCode() : 0);
        result = 31 * result + achievementsCount;
        result = 31 * result + (involvement != null ? involvement.hashCode() : 0);
        result = 31 * result + requiredLevel;
        result = 31 * result + (rewards != null ? rewards.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pictureUri=" + pictureUri +
                ", achievementsCount=" + achievementsCount +
                ", involvement=" + involvement +
                ", requiredLevel=" + requiredLevel +
                ", rewards=" + rewards +
                ", type=" + type +
                ", createdOn=" + createdOn +
                '}';
    }

    public enum Type {
        World(1),
        Daily(2),
        Weekly(3),
        Monthly(4);

        private int mId;

        Type(int id) {
            mId = id;
        }

        public int getId() {
            return mId;
        }

        public String getName() {
            return name();
        }
    }
}