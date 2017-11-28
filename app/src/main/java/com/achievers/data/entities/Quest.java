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

    @SerializedName("involvement")
    Involvement involvement;

    @SerializedName("experience")
    int experience;

    @SerializedName("requiredLevel")
    int requiredLevel;

    @SerializedName("achievements")
    List<Achievement> achievements;

    @SerializedName("completedAchievements")
    List<Achievement> completedAchievements;

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
            Involvement involvement,
            int experience,
            int requiredLevel,
            List<Achievement> achievements,
            List<Achievement> completedAchievements,
            List<Reward> rewards,
            Quest.Type type,
            Date createdOn) {

        this(id);
        this.name = name;
        this.pictureUri = pictureUri;
        this.involvement = involvement;
        this.experience = experience;
        this.requiredLevel = requiredLevel;
        this.achievements = achievements;
        this.completedAchievements = completedAchievements;
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

    public Involvement getInvolvement() {
        return involvement;
    }

    public int getExperience() {
        return experience;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public List<Achievement> getCompletedAchievements() {
        return completedAchievements;
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
        return String.format(Locale.getDefault(), "%d experience, %d level, %s",
                experience,
                requiredLevel,
                type.name());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quest quest = (Quest) o;

        if (id != quest.id) return false;
        if (requiredLevel != quest.requiredLevel) return false;
        if (name != null ? !name.equals(quest.name) : quest.name != null) return false;
        if (pictureUri != null ? !pictureUri.equals(quest.pictureUri) : quest.pictureUri != null)
            return false;
        if (involvement != quest.involvement) return false;
        if (experience != quest.experience) return false;
        if (achievements != null ? !achievements.equals(quest.achievements) : quest.achievements != null) return false;
        if (completedAchievements != null ? !completedAchievements.equals(quest.completedAchievements) : quest.completedAchievements != null) return false;
        if (rewards != null ? !rewards.equals(quest.rewards) : quest.rewards != null) return false;
        if (type != quest.type) return false;
        return createdOn != null ? createdOn.equals(quest.createdOn) : quest.createdOn == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pictureUri != null ? pictureUri.hashCode() : 0);
        result = 31 * result + (involvement != null ? involvement.hashCode() : 0);
        result = 31 * result + requiredLevel;
        result = 31 * result + experience;
        result = 31 * result + (achievements != null ? achievements.hashCode() : 0);
        result = 31 * result + (completedAchievements != null ? completedAchievements.hashCode() : 0);
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
                ", involvement=" + involvement +
                ", requiredLevel=" + requiredLevel +
                ", experience=" + experience +
                ", achievements=" + achievements +
                ", completedAchievements=" + completedAchievements +
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