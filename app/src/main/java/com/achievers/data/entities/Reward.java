package com.achievers.data.entities;

import com.achievers.data.entities._base.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

@Parcel(analyze = { Reward.class })
public class Reward implements BaseModel {

    @SerializedName("id")
    long id;

    @SerializedName("name")
    String name;

    @SerializedName("type")
    Reward.Type type;

    @SerializedName("createdOn")
    Date createdOn;

    public Reward() {

    }

    public Reward(long id) {
        this.id = id;
    }

    public Reward(
            long id,
            String name,
            Reward.Type type,
            Date createdOn) {

        this(id);
        this.name = name;
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

    public Reward.Type getType() {
        return type;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

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

        Reward reward = (Reward) o;

        if (id != reward.id) return false;
        if (name != null ? !name.equals(reward.name) : reward.name != null) return false;
        if (type != reward.type) return false;
        return createdOn != null ? createdOn.equals(reward.createdOn) : reward.createdOn == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        return result;
    }

    public enum Type {
        Experience(1),
        Item(2),
        Title(3);

        private int mId;

        Type(int id) {
            mId = id;
        }

        public int getId() {
            return mId;
        }
    }
}
