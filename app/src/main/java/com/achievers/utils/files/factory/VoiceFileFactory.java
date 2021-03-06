package com.achievers.utils.files.factory;

import android.content.Context;

import com.achievers.utils.files.FileUtils;

import java.io.File;
import java.io.IOException;

public class VoiceFileFactory extends BaseFileFactory {

    public VoiceFileFactory(Context context) {
        super(context);
    }

    public File createFile() throws IOException {
        return super.createFile(FileUtils.FileType.Voice);
    }
}