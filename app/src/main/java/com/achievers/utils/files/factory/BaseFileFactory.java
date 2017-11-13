package com.achievers.utils.files.factory;

import android.content.Context;

import com.achievers.utils.files.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public abstract class BaseFileFactory implements FileFactory {

    protected Context mContext;

    BaseFileFactory(Context context) {
        mContext = context;
    }

    protected File createFile(FileUtils.FileType fileType) throws IOException {
        return FileUtils.createFile(mContext, new Date(), fileType);
    }
}