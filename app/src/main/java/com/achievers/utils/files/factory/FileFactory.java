package com.achievers.utils.files.factory;

import android.net.Uri;

import java.io.File;
import java.io.IOException;

public interface FileFactory {

    File createFile() throws IOException;
    Uri getUri(File file);
}
