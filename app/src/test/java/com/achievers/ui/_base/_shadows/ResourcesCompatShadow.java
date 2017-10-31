package com.achievers.ui._base._shadows;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.annotation.FontRes;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(ResourcesCompat.class)
@Deprecated // todo: remove this class after Robolectric release support for ResourcesCompat.getFont
public class ResourcesCompatShadow {

    @Implementation
    public static Typeface getFont(@NonNull Context context, @FontRes int id)
            throws Resources.NotFoundException {

        return Typeface.DEFAULT;
    }
}