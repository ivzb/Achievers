package com.achievers.ui._base._shadows;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(Intent.class)
public class IntentShadow {

    @Implementation
    public ComponentName resolveActivity(PackageManager pm) {
        return new ComponentName("package", "class");
    }
}