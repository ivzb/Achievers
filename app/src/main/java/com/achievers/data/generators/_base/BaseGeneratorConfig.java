package com.achievers.data.generators._base;

import android.net.Uri;

import java.util.Date;

public interface BaseGeneratorConfig {

    long getId();

    String getWord();
    String getSentence();

    int getNumber();
    int getNumber(int bound);

    <T extends Enum<T>> T getEnum(T[] types);

    String getImageUrl();
    Uri getImageUri();

    String getVideoUrl();
    Uri getVideoUri();

    String getVoiceUrl();
    Uri getVoiceUri();

    Date getDate();
}