package com.achievers.data.sources.rewards;

import android.support.annotation.NonNull;

import com.achievers.data.Result;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.endpoints.RewardsAPI;
import com.achievers.data.entities.Reward;
import com.achievers.data.sources._base.BaseRemoteDataSource;

import java.util.List;

import retrofit2.Call;

public class RewardsRemoteDataSource
        extends BaseRemoteDataSource<Reward, RewardsAPI>
        implements RewardsDataSource {

    private static RewardsDataSource sINSTANCE;

    public static RewardsDataSource getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new RewardsRemoteDataSource();
        }

        return sINSTANCE;
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    // Prevent direct instantiation.
    private RewardsRemoteDataSource() {
        super(RewardsAPI.class);
    }

    @Override
    public void get(
            final String id,
            final @NonNull GetCallback<Reward> callback) {

        final Call<Result<Reward>> call = mApiService.get(id);
        call.enqueue(getCallback(callback));
    }

    @Override
    public void load(
            final String _,
            final int page,
            final @NonNull LoadCallback<Reward> callback) {

        final Call<Result<List<Reward>>> call = mApiService.load(page);
        call.enqueue(loadCallback(page, callback));
    }
}


