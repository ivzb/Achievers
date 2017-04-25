package com.achievers.data.source.remote;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.achievers.data.Achievement;
import com.achievers.data.Category;
import com.achievers.data.Involvement;
import com.achievers.data.source.AchievementsDataSource;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.bloco.faker.Faker;
import io.realm.Realm;

/**
 * Implementation of remote network data source.
 */
public class AchievementsRemoteDataSource implements AchievementsDataSource {

    private static AchievementsRemoteDataSource INSTANCE;
    private static final int SERVICE_LATENCY_IN_MILLIS = 500;

    // for developing purposes I am not fetching data from web service
    private final static Map<Integer, Achievement> ACHIEVEMENTS_SERVICE_DATA;

    static {
        ACHIEVEMENTS_SERVICE_DATA = new LinkedHashMap<>();
    }

    private static void generateAchievements(int count, final Category category, Faker faker)
    {
        if (count == 0) return;

        Achievement newAchievement = new Achievement(
                ACHIEVEMENTS_SERVICE_DATA.size() + 1, faker.lorem.word(), faker.lorem.sentence(5),
                "https://unsplash.it/180/180/?random", //&a=" + faker.number.number(2),
                category, Involvement.getRandomInvolvement(), faker.date.backward());

        ACHIEVEMENTS_SERVICE_DATA.put(newAchievement.getId(), newAchievement);

        generateAchievements(--count, category, faker);
    }

    public static AchievementsRemoteDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new AchievementsRemoteDataSource();
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private AchievementsRemoteDataSource() { }

    /**
     * Note: {@link LoadAchievementsCallback#onDataNotAvailable()} is fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void loadAchievements(final Integer categoryId, final @NonNull LoadAchievementsCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        final Category category = realm.where(Category.class).equalTo("id", categoryId).findFirstAsync();
        realm.close();

        // generating achievements on another thread so as not to block the ui thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                ACHIEVEMENTS_SERVICE_DATA.clear();
                generateAchievements(15, category, new Faker());
                final List<Achievement> achievementsToShow = new ArrayList<>(ACHIEVEMENTS_SERVICE_DATA.values());

                Handler mainHandler = new Handler(Looper.getMainLooper());
                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        callback.onLoaded(achievementsToShow);
                    }
                };
                mainHandler.post(myRunnable);
            }
        }).start();
    }

    /**
     * Note: {@link GetAchievementCallback#onDataNotAvailable()} is fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void getAchievement(@NonNull Integer id, final @NonNull GetAchievementCallback callback) {
        final Achievement achievement = ACHIEVEMENTS_SERVICE_DATA.get(id);

        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onLoaded(achievement);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void saveAchievement(@NonNull Achievement achievement) {
        // not implemented yet
    }

    @Override
    public void refreshCache() {
        // Not required because the {@link AchievementsRepository} handles the logic of refreshing the
        // Achievements from all the available data sources.
    }
}
