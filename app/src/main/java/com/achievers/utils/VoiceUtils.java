package com.achievers.utils;

import android.content.Context;
import android.os.Environment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.achievers.utils.Preconditions.checkNotNull;

public class VoiceUtils {

    private VoiceUtils() {

    }

    public static java.io.File createFile(Context context, Date date) throws IOException {
        checkNotNull(context);
        checkNotNull(date);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                .format(date);

        String prefix = "VOICE_" + timeStamp + "_";
        String suffix = ".3gp";
        java.io.File storageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return java.io.File.createTempFile(
                prefix,
                suffix,
                storageDirectory
        );
    }
}
