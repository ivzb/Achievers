package com.achievers.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtils {

    private static Context sContext;

    private SharedPreferencesUtils(Context context) {
        sContext = context;
    }

    public static void initialize(Context context) {
        sContext = context;
    }

    public static void setValue(
            int resId,
            String value) {

        SharedPreferences.Editor editor = getSharedPreferences().edit();
        String key = getKey(resId);
        editor.putString(key, value);
        editor.apply();
    }

    public static void setValue(
            int resId,
            int value) {

        SharedPreferences.Editor editor = getSharedPreferences().edit();
        String key = getKey(resId);
        editor.putInt(key, value);
        editor.apply();
    }

    public static void removeValue(int resId) {

        SharedPreferences.Editor editor = getSharedPreferences().edit();
        String key = getKey(resId);
        editor.remove(key);
        editor.apply();
    }

    public static String getValue(
            int resId,
            String defaultValue) {

        String key = sContext.getString(resId);

        return getSharedPreferences().getString(key, defaultValue);
    }

    public static int getValue(
            int resId,
            int defaultValue) {

        String key = sContext.getString(resId);

        return getSharedPreferences().getInt(key, defaultValue);
    }

    private static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(sContext);
    }

    private static String getKey(int resId) {
        return sContext.getString(resId);
    }
}