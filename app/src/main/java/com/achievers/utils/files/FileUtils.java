package com.achievers.utils.files;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.achievers.DefaultConfig;
import com.achievers.data.entities.File;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.achievers.utils.Preconditions.checkNotNull;

public class FileUtils {

    public static java.io.File createFile(
            Context context,
            Date date,
            FileType fileType) throws IOException {

        checkNotNull(context);
        checkNotNull(date);
        checkNotNull(fileType);

        String timeStamp = new SimpleDateFormat(DefaultConfig.DATE_FORMAT, Locale.US).format(date);
        String prefix = String.format("%s_%s_", fileType.getPrefix(), timeStamp);
        String suffix = fileType.getExtension();
        java.io.File storageDirectory = context.getExternalFilesDir(fileType.getDirectory());

        return java.io.File.createTempFile(
                prefix,
                suffix,
                storageDirectory
        );
    }

    public static File toFile(Context context, Uri uri)
            throws NullPointerException, IOException {

        checkNotNull(context);
        checkNotNull(uri);

        ContentResolver contentResolver = context.getContentResolver();

        InputStream stream = contentResolver.openInputStream(uri);

        byte[] byteArray = IOUtils.toByteArray(stream);
        String contentType = contentResolver.getType(uri);

        return new File(byteArray, contentType, uri);
    }

    public enum FileType {

        Picture("JPEG", ".jpg", Environment.DIRECTORY_PICTURES),
        Voice("VOICE", ".3gp", Environment.DIRECTORY_MUSIC);

        private String mPrefix;
        private String mExtension;
        private String mDirectory;

        FileType(String prefix, String extension, String directory) {
            mPrefix = prefix;
            mExtension = extension;
            mDirectory = directory;
        }

        String getPrefix() {
            return mPrefix;
        }
        String getExtension() {
            return mExtension;
        }
        String getDirectory() {
            return mDirectory;
        }
    }
}
