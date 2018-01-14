package com.achievers.data.entities;

import android.net.Uri;

import com.achievers.data.entities._base.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

@Parcel(analyze = { Quest.class })
public class Quest implements BaseModel {

    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("pictureUri")
    Uri pictureUri;

    @SerializedName("experience")
    int experience;

    @SerializedName("involvement")
    Involvement involvement;

//    @SerializedName("parents")
//    List<Long> parents;

    @SerializedName("achievements")
    List<Achievement> achievements;

    @SerializedName("completed")
    TreeSet<String> completed;

    @SerializedName("rewards")
    List<Reward> rewards;

    @SerializedName("type")
    Quest.Type type;

    @SerializedName("createdOn")
    Date createdOn;

    public Quest() {

    }

    public Quest(String id) {
        this.id = id;
    }

    public Quest(
            String id,
            String name,
            Uri pictureUri,
            Involvement involvement,
            int experience,
            List<Achievement> achievements,
            TreeSet<String> completed,
            List<Reward> rewards,
            Quest.Type type,
            Date createdOn) {

        this(id);
        this.name = name;
        this.pictureUri = pictureUri;
        this.involvement = involvement;
        this.experience = experience;
        this.achievements = achievements;
        this.completed = completed;
        this.rewards = rewards;
        this.type = type;
        this.createdOn = createdOn;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
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

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public TreeSet<String> getCompleted() {
        return completed;
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
    public String getContainerId() {
        return null;
    }

    public String getDescription() {
        return String.format(Locale.getDefault(), "%d experience, %s",
                experience,
                type.name());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quest quest = (Quest) o;

        if (experience != quest.experience) return false;
        if (id != null ? !id.equals(quest.id) : quest.id != null) return false;
        if (name != null ? !name.equals(quest.name) : quest.name != null) return false;
        if (pictureUri != null ? !pictureUri.equals(quest.pictureUri) : quest.pictureUri != null)
            return false;
        if (involvement != quest.involvement) return false;
        if (achievements != null ? !achievements.equals(quest.achievements) : quest.achievements != null)
            return false;
        if (completed != null ? !completed.equals(quest.completed) : quest.completed != null)
            return false;
        if (rewards != null ? !rewards.equals(quest.rewards) : quest.rewards != null) return false;
        if (type != quest.type) return false;
        return createdOn != null ? createdOn.equals(quest.createdOn) : quest.createdOn == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pictureUri != null ? pictureUri.hashCode() : 0);
        result = 31 * result + experience;
        result = 31 * result + (involvement != null ? involvement.hashCode() : 0);
        result = 31 * result + (achievements != null ? achievements.hashCode() : 0);
        result = 31 * result + (completed != null ? completed.hashCode() : 0);
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
                ", experience=" + experience +
                ", achievements=" + achievements +
                ", completedAchievements=" + completed +
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