package com.achievers.data.entities;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class File {

    @SerializedName("Id")
    @NonNull
    long id;

    @SerializedName("Name")
    @NonNull
    String name;

    @SerializedName("Content")
    @NonNull
    byte[] content;

    @SerializedName("ContentType")
    @NonNull
    String contentType;

    public File(@NonNull byte[] content, @NonNull String contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return this.content;
    }

    public String getContentType() {
        return this.contentType;
    }
}
