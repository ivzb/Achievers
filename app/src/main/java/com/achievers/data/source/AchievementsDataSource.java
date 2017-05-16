package com.achievers.data.source;

import android.support.annotation.NonNull;

import com.achievers.data.Achievement;

import java.util.List;

/**
 * Main entry point for accessing Categories data.
 */
public interface AchievementsDataSource {
    interface GetAchievementCallback {

        void onLoaded(Achievement achievement);

        void onDataNotAvailable();
    }

    interface LoadAchievementsCallback {

        void onLoaded(List<Achievement> achievements);

        void onDataNotAvailable();
    }

    void getAchievement(@NonNull Integer id, @NonNull GetAchievementCallback callback);

    void loadAchievements(Integer categoryId, @NonNull LoadAchievementsCallback callback);

    void saveAchievements(@NonNull List<Achievement> achievements);

    void refreshCache();
}