package com.achievers.ui._base._shadows;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import java.io.File;

@Implements(FileProvider.class)
public class FileProviderShadow {

    @Implementation
    public static Uri getUriForFile(Context context, String authority, File file) {
        return Uri.parse("shadow_uri");
    }
}
