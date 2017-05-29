package com.achievers.data.source;

import android.support.annotation.NonNull;

import com.achievers.data.Achievement;
import com.achievers.data.source.callbacks.GetCallback;
import com.achievers.data.source.callbacks.LoadCallback;
import com.achievers.data.source.callbacks.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Main entry point for accessing Categories data.
 */
public interface AchievementsDataSource {

    void getAchievement(final int id, @NonNull GetCallback<Achievement> callback);

    void loadAchievements(final int categoryId, final int page, @NonNull LoadCallback<ArrayList<Achievement>> callback);

    void saveAchievements(@NonNull final List<Achievement> achievements, @NonNull SaveCallback<List<Achievement>> callback);

    void refreshCache();
}