package com.achievers.utils;

import android.database.Cursor;

public class CursorUtils {

    public static int getSize(Cursor cursor) {
        if (null == cursor) return 0;
        return cursor.getCount();
    }
}