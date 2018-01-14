package com.achievers.data.entities;

import android.net.Uri;

import com.achievers.data.entities._base.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

@Parcel(analyze = { Profile.class })
public class Profile implements BaseModel {

    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("pictureUri")
    Uri pictureUri;

    @SerializedName("createdOn")
    Date createdOn;

    public Profile() {

    }

    public Profile(String id) {
        this.id = id;
    }

    public Profile(
            String id,
            String name,
            Uri pictureUri,
            Date createdOn) {

        this(id);
        this.name = name;
        this.pictureUri = pictureUri;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        if (id != null ? !id.equals(profile.id) : profile.id != null) return false;
        if (name != null ? !name.equals(profile.name) : profile.name != null) return false;
        if (pictureUri != null ? !pictureUri.equals(profile.pictureUri) : profile.pictureUri != null)
            return false;
        return createdOn != null ? createdOn.equals(profile.createdOn) : profile.createdOn == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pictureUri != null ? pictureUri.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pictureUri=" + pictureUri +
                ", createdOn=" + createdOn +
                '}';
    }
}