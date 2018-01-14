package com.achievers.data.entities;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class File {

    @SerializedName("Id")
    @NonNull
    String id;

    @SerializedName("Name")
    @NonNull
    String name;

    @SerializedName("Content")
    @NonNull
    byte[] content;

    @SerializedName("ContentType")
    @NonNull
    String contentType;

    @Deprecated // used for memory file storage
    private Uri mUri;

    public File(@NonNull byte[] content, @NonNull String contentType, @Deprecated Uri uri) {
        this.content = content;
        this.contentType = contentType;
        this.mUri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getContent() {
        return this.content;
    }

    public String getContentType() {
        return this.contentType;
    }

    public Uri getUri() {
        return mUri;
    }
}
