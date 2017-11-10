package com.achievers.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.achievers.data.entities.File;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.achievers.utils.Preconditions.checkNotNull;

public class PictureUtils {

    private PictureUtils() {

    }

    public static File toFile(Context context, Uri imageUri)
            throws FileNotFoundException, NullPointerException {

        checkNotNull(context);
        checkNotNull(imageUri);

        Bitmap bitmap;

        InputStream imageStream = context
                .getContentResolver()
                .openInputStream(imageUri);

        bitmap = BitmapFactory.decodeStream(imageStream);

        if (bitmap == null) {
            throw new FileNotFoundException();
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return new File(byteArray, "image/jpeg");
    }
}
