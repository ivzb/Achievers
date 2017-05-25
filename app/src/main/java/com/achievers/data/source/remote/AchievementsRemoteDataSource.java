package com.achievers.data.source.remote;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.achievers.Achievements.AchievementsEndpointInterface;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of remote network data source.
 */
public class AchievementsRemoteDataSource implements AchievementsDataSource {

    private static AchievementsRemoteDataSource INSTANCE;

    private AchievementsEndpointInterface apiService;

    // for developing purposes I am not fetching data from web service
//    private final static Map<Integer, Achievement> ACHIEVEMENTS_SERVICE_DATA;

//    static {
//        ACHIEVEMENTS_SERVICE_DATA = new LinkedHashMap<>();
//    }

//    private static void generateAchievements(int count, final Category category, Faker faker)
//    {
//        if (count == 0) return;
//
//        Achievement newAchievement = new Achievement(
//                ACHIEVEMENTS_SERVICE_DATA.size() + 1, faker.lorem.word(), faker.lorem.sentence(5),
//                "https://unsplash.it/500/500/?random&a=" + faker.number.number(2),
//                category, Involvement.getRandomInvolvement(), faker.date.backward());
//
//        ACHIEVEMENTS_SERVICE_DATA.put(newAchievement.getId(), newAchievement);
//
//        generateAchievements(--count, category, faker);
//    }

    public static AchievementsRemoteDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new AchievementsRemoteDataSource();
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private AchievementsRemoteDataSource() {
        this.apiService = RESTClient
                .getClient()
                .create(AchievementsEndpointInterface.class);
    }

    /**
     * Note: {@link LoadAchievementsCallback#onDataNotAvailable()} is fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void loadAchievements(final int categoryId, final @NonNull LoadAchievementsCallback callback) {
//        Realm realm = Realm.getDefaultInstance();
//        final Category category = realm.where(Category.class).equalTo("id", categoryId).findFirstAsync();
//        realm.close();
//
//        // generating achievements on another thread so as not to block the ui thread
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ACHIEVEMENTS_SERVICE_DATA.clear();
//                generateAchievements(15, category, new Faker());
//                final List<Achievement> achievementsToShow = new ArrayList<>(ACHIEVEMENTS_SERVICE_DATA.values());
//
//                Handler mainHandler = new Handler(Looper.getMainLooper());
//                Runnable myRunnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        callback.onLoaded(achievementsToShow);
//                    }
//                };
//                mainHandler.post(myRunnable);
//            }
//        }).start();

        final Call<ODataResponseArray<Achievement>> call = this.apiService.getAchievements(categoryId);

        call.enqueue(new Callback<ODataResponseArray<Achievement>>() {
            @Override
            public void onResponse(Call<ODataResponseArray<Achievement>> call, Response<ODataResponseArray<Achievement>> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onDataNotAvailable();
                    return;
                }

                List<Achievement> achievements = response.body().getResult();
                callback.onLoaded(achievements);
            }

            @Override
            public void onFailure(Call<ODataResponseArray<Achievement>> call, Throwable t) {
                // Log error here since request failed
                callback.onDataNotAvailable();
            }
        });
    }

    /**
     * Note: {@link GetAchievementCallback#onDataNotAvailable()} is fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void getAchievement(final int id, final @NonNull GetAchievementCallback callback) {
//        final Achievement achievement = ACHIEVEMENTS_SERVICE_DATA.get(id);
//        callback.onLoaded(achievement);
        final Call<Achievement> call = this.apiService.getAchievement(id);

        call.enqueue(new Callback<Achievement>() {
            @Override
            public void onResponse(Call<Achievement> call, Response<Achievement> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onDataNotAvailable();
                    return;
                }

                Achievement achievement = response.body();
                callback.onLoaded(achievement);
            }

            @Override
            public void onFailure(Call<Achievement> call, Throwable t) {
                // Log error here since request failed
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveAchievements(@NonNull List<Achievement> achievements, @NonNull final SaveAchievementsCallback callback) {
        // not being used
    }

    @Override
    public void refreshCache() {
        // Not required because the {@link AchievementsRepository} handles the logic of refreshing the
        // Achievements from all the available data sources.
    }
}