package com.achievers.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.achievers.utils.Preconditions.checkNotNull;

public class PictureUtils {

    private PictureUtils() {

    }

    public static File createFile(Context context, Date date) throws IOException {
        checkNotNull(context);
        checkNotNull(date);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                .format(date);

        String prefix = "JPEG_" + timeStamp + "_";
        String suffix = ".jpg";
        File storageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                prefix,
                suffix,
                storageDirectory
        );
    }
}
