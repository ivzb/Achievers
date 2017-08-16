package com.achievers.data.source.files;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.models.File;

public interface FilesDataSource {

    void storeFile(@NonNull File file, @NonNull SaveCallback<File> callback);
}