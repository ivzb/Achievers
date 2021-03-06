package com.achievers.data.generators._base.contracts;

import android.net.Uri;

import com.achievers.data.entities._base.BaseModel;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;

public interface BaseGeneratorConfig {

    String getId();
    String getEmail();
    String getPassword();
    String getAuthenticationToken();

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

    <T extends BaseModel> List<T> getAmong(List<T> entities, int resultsSize);
    <T extends BaseModel> TreeSet<String> getIdsAmong(List<T> entities, int resultsSize);
}