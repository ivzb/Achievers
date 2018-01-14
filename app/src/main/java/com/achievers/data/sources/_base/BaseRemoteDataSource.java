package com.achievers.data.sources._base;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.sources.RESTClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseRemoteDataSource<M, API> {

    private static final String sNetworkError = "Server could not be reached. Please try again.";

    protected API mApiService;

    // Prevent direct instantiation.
    protected BaseRemoteDataSource(final Class<API> service) {
        mApiService = RESTClient
                .getClient()
                .create(service);
    }

    protected Callback<M> getCallback(final GetCallback<M> callback) {
        return new Callback<M>() {
            @Override
            public void onResponse(Call<M> call, Response<M> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure(response.message());
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<M> call, Throwable t) {
                callback.onFailure(sNetworkError);
            }
        };
    }

    protected Callback<List<M>> loadCallback(
            final int page,
            final LoadCallback<M> callback) {

        return new Callback<List<M>>() {
            @Override
            public void onResponse(Call<List<M>> call, Response<List<M>> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure(response.message());
                    return;
                }

                callback.onSuccess(response.body(), page);
            }

            @Override
            public void onFailure(Call<List<M>> call, Throwable t) {
                callback.onFailure(sNetworkError);
            }
        };
    }

    protected Callback<String> saveCallback(
            final SaveCallback<String> callback) {

        return new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure(response.message());
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure(sNetworkError);
            }
        };
    }
}
