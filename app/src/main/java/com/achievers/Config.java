package com.achievers;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

public final class Config {

    public static final int RECYCLER_INITIAL_PAGE = 0;
    public static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
    public static final long NO_ID = -1L;

    public static final int PlaceholderImageResource = R.drawable.bunny;
    public static Drawable PlaceholderImage;

    public static final String PlaceholderText = "Achievers";

    private Config() {

    }

    public static void initialize(Resources resources) {
        PlaceholderImage = ResourcesCompat.getDrawable(
                resources,
                PlaceholderImageResource,
                null);
    }
}