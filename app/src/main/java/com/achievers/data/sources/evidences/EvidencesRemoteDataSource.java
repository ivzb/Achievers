package com.achievers.data.sources.evidences;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.endpoints.EvidenceAPI;
import com.achievers.data.entities.Evidence;
import com.achievers.data.sources.RESTClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of remote network data source.
 */
public class EvidencesRemoteDataSource implements EvidencesDataSource {

    private static EvidencesDataSource sINSTANCE;

    private EvidenceAPI apiService;

    public static EvidencesDataSource getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new EvidencesRemoteDataSource();
        }

        return sINSTANCE;
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    // Prevent direct instantiation.
    private EvidencesRemoteDataSource() {
        this.apiService = RESTClient
                .getClient()
                .create(EvidenceAPI.class);
    }

    @Override
    public void get(
            final String id,
            final @NonNull GetCallback<Evidence> callback) {

        final Call<Evidence> call = this.apiService.getEvidence(id);

        call.enqueue(new Callback<Evidence>() {
            @Override
            public void onResponse(Call<Evidence> call, Response<Evidence> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure("Error occurred. Please try again.");
                    return;
                }

                Evidence evidence = response.body();
                callback.onSuccess(evidence);
            }

            @Override
            public void onFailure(Call<Evidence> call, Throwable t) {
                callback.onFailure("Server could not be reached. Please try again.");
            }
        });
    }

    @Override
    public void load(
            final String achievementId,
            final int page,
            final @NonNull LoadCallback<Evidence> callback) {

        final Call<List<Evidence>> call = this.apiService.loadByAchievement(achievementId/*, pageSize, page * pageSize*/);

        call.enqueue(new Callback<List<Evidence>>() {
            @Override
            public void onResponse(Call<List<Evidence>> call, Response<List<Evidence>> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure("Error occurred. Please try again.");
                    return;
                }

                callback.onSuccess(response.body(), page);
            }

            @Override
            public void onFailure(Call<List<Evidence>> call, Throwable t) {
                callback.onFailure("Server could not be reached. Please try again.");
            }
        });
    }

    @Override
    public void save(
            @NonNull Evidence evidence,
            @NonNull final SaveCallback<String> callback) {

        // not implemented yet
    }
}
