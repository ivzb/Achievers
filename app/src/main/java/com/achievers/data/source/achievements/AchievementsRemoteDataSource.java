package com.achievers.data.source.achievements;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.endpoints.AchievementsAPI;
import com.achievers.models.Achievement;
import com.achievers.data.source.RESTClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of remote network data source.
 */
public class AchievementsRemoteDataSource implements AchievementsDataSource {

    private static AchievementsRemoteDataSource INSTANCE;

    private AchievementsAPI apiService;
    private final int pageSize = RESTClient.getPageSize();

    public static AchievementsRemoteDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new AchievementsRemoteDataSource();
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private AchievementsRemoteDataSource() {
        this.apiService = RESTClient
                .getClient()
                .create(AchievementsAPI.class);
    }

    @Override
    public void loadAchievements(
            final int categoryId,
            final int page,
            final @NonNull LoadCallback<Achievement> callback) {

        final Call<List<Achievement>> call = this.apiService.loadByCategory(categoryId/*, pageSize, page * pageSize*/);

        call.enqueue(new Callback<List<Achievement>>() {
            @Override
            public void onResponse(Call<List<Achievement>> call, Response<List<Achievement>> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure("Error occurred. Please try again.");
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Achievement>> call, Throwable t) {
                callback.onFailure("Server could not be reached. Please try again.");
            }
        });
    }

    @Override
    public void getAchievement(
            final int id,
            final @NonNull GetCallback<Achievement> callback) {

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
    public void saveAchievement(
            @NonNull Achievement achievement,
            @NonNull SaveCallback<Void> callback) {

        // todo
    }
}