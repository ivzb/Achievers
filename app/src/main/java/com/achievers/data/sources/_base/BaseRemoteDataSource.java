package com.achievers.data.sources._base;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseRemoteDataSource<T> {

    private static final String sNetworkError = "Server could not be reached. Please try again.";

    protected Callback<T> getCallback(final GetCallback<T> callback) {
        return new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure(response.message());
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callback.onFailure(sNetworkError);
            }
        };
    }

    protected Callback<List<T>> loadCallback(
            final int page,
            final LoadCallback<T> callback) {

        return new Callback<List<T>>() {
            @Override
            public void onResponse(Call<List<T>> call, Response<List<T>> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure(response.message());
                    return;
                }

                callback.onSuccess(response.body(), page);
            }

            @Override
            public void onFailure(Call<List<T>> call, Throwable t) {
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
