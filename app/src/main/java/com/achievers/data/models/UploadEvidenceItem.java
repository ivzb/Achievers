package com.achievers.data.models;

import android.graphics.drawable.Drawable;

public class UploadEvidenceItem {
    private String title;
    private Drawable drawable;

    public UploadEvidenceItem(String title, Drawable drawable) {
        this.title = title;
        this.drawable = drawable;
    }

    public String getTitle() {
        return this.title;
    }

    public Drawable getImage() {
        return this.drawable;
    }
}