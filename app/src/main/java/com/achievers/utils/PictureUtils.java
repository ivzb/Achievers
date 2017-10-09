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

public class PictureUtils {

    private PictureUtils() {

    }

    public static File createFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                .format(new Date());

        String prefix = "JPEG_" + timeStamp + "_";
        String suffix = ".jpg";
        File storageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                prefix,
                suffix,
                storageDirectory
        );
    }

//    public static int getOrientation(Context context, Uri photoUri) {
//        Cursor cursor = context.getContentResolver().query(
//                photoUri,
//                new String[] { MediaStore.Images.ImageColumns.ORIENTATION },
//                null,
//                null,
//                null);
//
//        int result = -1;
//
//        if (null != cursor) {
//            if (cursor.moveToFirst()) {
//                result = cursor.getInt(0);
//            }
//
//            cursor.close();
//        }
//
//        return result;
//    }

    public static Bitmap rotate(Bitmap bitmap, int orientation) throws OutOfMemoryError {
        Matrix matrix = new Matrix();

        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }

        return Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.getWidth(),
                bitmap.getHeight(),
                matrix,
                true);
    }

    public static Bitmap scale(Bitmap bitmap, int targetWidth) {
        float aspectRatio = bitmap.getWidth() / (float) bitmap.getHeight();
        int height = Math.round(targetWidth / aspectRatio);

        // to use height as base instead of with
//        int height = mTargetHeight;
//        int width = Math.round(height * aspectRatio);

        return Bitmap.createScaledBitmap(
                bitmap, targetWidth, height, false);
    }
}
