package com.achievers.data.source.evidence;

import android.support.annotation.NonNull;

import com.achievers.data.endpoints.EvidenceAPI;
import com.achievers.data.models.Evidence;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.source.RESTClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of remote network data source.
 */
public class EvidenceRemoteDataSource implements EvidenceDataSource {

    private static EvidenceRemoteDataSource INSTANCE;

    private EvidenceAPI apiService;
    private final int pageSize = RESTClient.getPageSize();

    public static EvidenceRemoteDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new EvidenceRemoteDataSource();
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private EvidenceRemoteDataSource() {
        this.apiService = RESTClient
                .getClient()
                .create(EvidenceAPI.class);
    }

    @Override
    public void loadEvidence(
            final int achievementId,
            final int page,
            final @NonNull LoadCallback<List<Evidence>> callback
    ) {
        final Call<List<Evidence>> call = this.apiService.loadByAchievement(achievementId/*, pageSize, page * pageSize*/);

        call.enqueue(new Callback<List<Evidence>>() {
            @Override
            public void onResponse(Call<List<Evidence>> call, Response<List<Evidence>> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure("Error occurred. Please try again.");
                    return;
                }

                List<Evidence> evidence = response.body();

                if (evidence.isEmpty()) {
                    callback.onNoMoreData();
                    return;
                }

                callback.onSuccess(evidence);
            }

            @Override
            public void onFailure(Call<List<Evidence>> call, Throwable t) {
                callback.onFailure("Server could not be reached. Please try again.");
            }
        });
    }

    @Override
    public void getEvidence(
            final int id,
            final @NonNull GetCallback<Evidence> callback
    ) {
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
    public void saveEvidence(
            @NonNull List<Evidence> evidence,
            @NonNull final SaveCallback<Void> callback
    ) {
        // not implemented yet
    }

    @Override
    public void refreshCache() {
        // Not required because the {@link EvidenceRepository} handles the logic of refreshing the
        // Evidence from all the available data sources.
    }
}
