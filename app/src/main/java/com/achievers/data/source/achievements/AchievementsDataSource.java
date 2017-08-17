package com.achievers.data.source.achievements;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.entities.Achievement;

/**
 * Main entry point for accessing Categories data.
 */
public interface AchievementsDataSource {

    void getAchievement(
            final int id,
            @NonNull final GetCallback<Achievement> callback
    );

    void loadAchievements(
            final int categoryId,
            final int page,
            @NonNull final LoadCallback<Achievement> callback
    );

    void saveAchievement(
            @NonNull final Achievement achievement,
            @NonNull final SaveCallback<Void> callback
    );
}