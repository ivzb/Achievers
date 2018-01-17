package com.achievers;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

public final class DefaultConfig {

    public static final int PAGE = 0;
    public static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
    public static final String NO_ID = "";

    public static final int PlaceholderImageResource = R.drawable.bunny;
    public static Drawable PlaceholderImage;

    public static final String PlaceholderText = "Achievers";

    private DefaultConfig() {

    }

    public static void initialize(Resources resources) {
        PlaceholderImage = ResourcesCompat.getDrawable(
                resources,
                PlaceholderImageResource,
                null);
    }
}