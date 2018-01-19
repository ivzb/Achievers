package com.achievers.data.sources._base;

import com.achievers.data.Result;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.sources.RESTClient;
import com.google.gson.Gson;

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

    protected Callback<Result<M>> getCallback(final GetCallback<M> callback) {
        return new Callback<Result<M>>() {
            @Override
            public void onResponse(Call<Result<M>> call, Response<Result<M>> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    Result result = new Gson().fromJson(
                            response.errorBody().charStream(),
                            Result.class);

                    callback.onFailure(result.getMessage());
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Result<M>> call, Throwable t) {
                callback.onFailure(sNetworkError);
            }
        };
    }

    protected Callback<Result<List<M>>> loadCallback(
            final int page,
            final LoadCallback<M> callback) {

        return new Callback<Result<List<M>>>() {
            @Override
            public void onResponse(Call<Result<List<M>>> call, Response<Result<List<M>>> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure(response.message());
                    return;
                }

                Result<List<M>> result = response.body();

                if (result.getResults() == null) {
                    callback.onNoMore();
                    return;
                }

                callback.onSuccess(result, page);
            }

            @Override
            public void onFailure(Call<Result<List<M>>> call, Throwable t) {
                callback.onFailure(sNetworkError);
            }
        };
    }

    protected Callback<Result<String>> saveCallback(
            final SaveCallback<String> callback) {

        return new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure(response.message());
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                callback.onFailure(sNetworkError);
            }
        };
    }
}
