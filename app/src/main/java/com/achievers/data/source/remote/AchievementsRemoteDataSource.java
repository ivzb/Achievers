package com.achievers.data.source.remote;

import android.support.annotation.NonNull;

import com.achievers.Achievements.AchievementsEndpointInterface;
import com.achievers.data.Achievement;
import com.achievers.data.source.AchievementsDataSource;
import com.achievers.data.source.callbacks.GetCallback;
import com.achievers.data.source.callbacks.LoadCallback;
import com.achievers.data.source.callbacks.SaveCallback;

import java.util.List;

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
    public void loadAchievements(
            final int categoryId,
            final int page,
            final @NonNull LoadCallback<List<Achievement>> callback
    ) {
        final Call<ODataResponseArray<Achievement>> call = this.apiService.loadAchievements(categoryId, pageSize, page * pageSize);

        call.enqueue(new Callback<ODataResponseArray<Achievement>>() {
            @Override
            public void onResponse(Call<ODataResponseArray<Achievement>> call, Response<ODataResponseArray<Achievement>> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure("Error occurred. Please try again.");
                    return;
                }

                List<Achievement> achievements = response.body().getResult();

                if (achievements.isEmpty()) {
                    callback.onNoMoreData();
                    return;
                }

                callback.onSuccess(achievements);
            }

            @Override
            public void onFailure(Call<ODataResponseArray<Achievement>> call, Throwable t) {
                callback.onFailure("Server could not be reached. Please try again.");
            }
        });
    }

    @Override
    public void getAchievement(
            final int id,
            final @NonNull GetCallback<Achievement> callback
    ) {
        final Call<Achievement> call = this.apiService.getAchievement(id);

        call.enqueue(new Callback<Achievement>() {
            @Override
            public void onResponse(Call<Achievement> call, Response<Achievement> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure("Error occurred. Please try again.");
                    return;
                }

                Achievement achievement = response.body();
                callback.onSuccess(achievement);
            }

            @Override
            public void onFailure(Call<Achievement> call, Throwable t) {
                callback.onFailure("Server could not be reached. Please try again.");
            }
        });
    }

    @Override
    public void saveAchievements(
            @NonNull List<Achievement> achievements,
            @NonNull final SaveCallback<Void> callback
    ) {
        // not being used
    }

    @Override
    public void refreshCache() {
        // Not required because the {@link AchievementsRepository} handles the logic of refreshing the
        // Achievements from all the available data sources.
    }
}