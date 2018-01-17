package com.achievers.data.sources.achievements;

import android.support.annotation.NonNull;

import com.achievers.data.Result;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.endpoints.AchievementsAPI;
import com.achievers.data.entities.Achievement;
import com.achievers.data.sources._base.BaseRemoteDataSource;

import java.util.List;

import retrofit2.Call;

/**
 * Implementation of remote network data source.
 */
public class AchievementsRemoteDataSource
        extends BaseRemoteDataSource<Achievement, AchievementsAPI>
        implements AchievementsDataSource {

    private static AchievementsDataSource sINSTANCE;

    public static AchievementsDataSource getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new AchievementsRemoteDataSource();
        }

        return sINSTANCE;
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    // Prevent direct instantiation.
    private AchievementsRemoteDataSource() {
        super(AchievementsAPI.class);
    }

    @Override
    public void get(
            final String id,
            final @NonNull GetCallback<Achievement> callback) {

        final Call<Result<Achievement>> call = mApiService.get(id);
        call.enqueue(getCallback(callback));
    }

    @Override
    public void load(
            final String _,
            final int page,
            final @NonNull LoadCallback<Achievement> callback) {

        final Call<Result<List<Achievement>>> call = mApiService.load(page);
        call.enqueue(loadCallback(page, callback));
    }

    @Override
    public void loadByQuestId(
            String questId,
            int page,
            LoadCallback<Achievement> callback) {

        final Call<Result<List<Achievement>>> call = mApiService.loadByQuest(questId, page);
        call.enqueue(loadCallback(page, callback));
    }

    @Override
    public void save(
            @NonNull Achievement achievement,
            @NonNull SaveCallback<String> callback) {

        final Call<Result<String>> call = mApiService.create(achievement);
        call.enqueue(saveCallback(callback));
    }
}