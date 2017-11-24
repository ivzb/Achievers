package com.achievers.data.entities;

import android.net.Uri;

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

    @SerializedName("description")
    String description;

    @SerializedName("pictureUri")
    Uri pictureUri;

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
            String description,
            Uri pictureUri,
            Reward.Type type,
            Date createdOn) {

        this(id);
        this.name = name;
        this.description = description;
        this.pictureUri = pictureUri;
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

    public String getDescription() {
        return description;
    }

    public Uri getPictureUri() {
        return pictureUri;
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
        if (description != null ? !description.equals(reward.description) : reward.description != null)
            return false;
        if (pictureUri != null ? !pictureUri.equals(reward.pictureUri) : reward.pictureUri != null)
            return false;
        if (type != reward.type) return false;
        return createdOn != null ? createdOn.equals(reward.createdOn) : reward.createdOn == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (pictureUri != null ? pictureUri.hashCode() : 0);
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