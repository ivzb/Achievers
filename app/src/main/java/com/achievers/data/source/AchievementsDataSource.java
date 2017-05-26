package com.achievers.data.source;

import android.support.annotation.NonNull;

import com.achievers.data.Achievement;

import java.util.ArrayList;
import java.util.List;

/**
 * Main entry point for accessing Categories data.
 */
public interface AchievementsDataSource {
    interface GetAchievementCallback {
        void onLoaded(Achievement achievement);
        void onDataNotAvailable();
    }



    interface SaveAchievementsCallback {
        void onSuccess();
        void onError();
    }

    void getAchievement(final int id, @NonNull GetAchievementCallback callback);

    void loadAchievements(final int categoryId, @NonNull LoadCallback<ArrayList<Achievement>> callback);

    void saveAchievements(@NonNull List<Achievement> achievements, @NonNull SaveAchievementsCallback callback);

    void refreshCache();
}