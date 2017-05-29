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
import com.achievers.data.source.LoadCallback;
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
    private final int pageSize = RESTClient.getPageSize();

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

    @Override
    public void loadAchievements(final int categoryId, final int page, final @NonNull LoadCallback<ArrayList<Achievement>> callback) {
        final Call<ODataResponseArray<Achievement>> call = this.apiService.getAchievements(categoryId, pageSize, page * pageSize);

        call.enqueue(new Callback<ODataResponseArray<Achievement>>() {
            @Override
            public void onResponse(Call<ODataResponseArray<Achievement>> call, Response<ODataResponseArray<Achievement>> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure("Error occurred. Please try again.");
                    return;
                }

                ArrayList<Achievement> achievements = response.body().getResult();

                if (achievements.isEmpty()) {
                    callback.onNoMoreData();
                } else {
                    callback.onSuccess(achievements);
                }
            }

            @Override
            public void onFailure(Call<ODataResponseArray<Achievement>> call, Throwable t) {
                // Log error here since request failed
                callback.onFailure("Server could not be reached. Please try again.");
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