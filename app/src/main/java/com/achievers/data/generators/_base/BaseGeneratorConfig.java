package com.achievers.data.generators._base;

import android.net.Uri;

import com.achievers.data.entities._base.BaseModel;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;

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

    <T extends BaseModel> List<T> getAmong(List<T> entities);
    <T extends BaseModel> TreeSet<Long> getIdsAmong(List<T> entities);
}