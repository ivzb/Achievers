package com.achievers.data.source.categories;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.endpoints.CategoriesAPI;
import com.achievers.entities.Category;
import com.achievers.data.source.RESTClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of remote network data source.
 */
public class CategoriesRemoteDataSource implements CategoriesDataSource {

    private static CategoriesDataSource INSTANCE;

    private CategoriesAPI apiService;

    public static CategoriesDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new CategoriesRemoteDataSource();

        return INSTANCE;
    }

    // Prevent direct instantiation.
    private CategoriesRemoteDataSource() {
         this.apiService = RESTClient
                .getClient()
                .create(CategoriesAPI.class);
    }

    @Override
    public void loadCategories(@NonNull final LoadCallback<Category> callback) {
        this.apiService
            .loadCategories()
            .enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    int statusCode = response.code();

                    if (statusCode != 200) {
                        callback.onFailure("Error occurred. Please try again.");
                        return;
                    }

                    callback.onSuccess(response.body());
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {
                    callback.onFailure("Server could not be reached. Please try again.");
                }
            });
    }

    @Override
    public void getCategory(
            @NonNull final Integer categoryId,
            @NonNull final GetCallback<Category> callback) {

        final Call<Category> call = this.apiService.getCategory(categoryId);

        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure(null);
                    return;
                }

                Category category = response.body();
                callback.onSuccess(category);
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                callback.onFailure("Server could not be reached. Please try again.");
            }
        });
    }
}