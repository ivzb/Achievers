package com.achievers.data.sources.contributions;

import com.achievers.data.Result;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.endpoints.ContributionsAPI;
import com.achievers.data.entities.Contribution;
import com.achievers.data.sources._base.BaseRemoteDataSource;

import java.util.List;

import retrofit2.Call;

public class ContributionsRemoteDataSource
    extends BaseRemoteDataSource<Contribution, ContributionsAPI>
    implements ContributionsDataSource {

    private static ContributionsDataSource sINSTANCE;

    public static ContributionsDataSource getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new ContributionsRemoteDataSource();
        }

        return sINSTANCE;
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    // Prevent direct instantiation.
    private ContributionsRemoteDataSource() {
        super(ContributionsAPI.class);
    }

    @Override
    public void get(String id, GetCallback<Contribution> callback) {

        final Call<Result<Contribution>> call = mApiService.get(id);
        call.enqueue(getCallback(callback));
    }

    @Override
    public void load(String id, int page, LoadCallback<Contribution> callback) {

        final Call<Result<List<Contribution>>> call = mApiService.load(page);
        call.enqueue(loadCallback(page, callback));
    }
}
