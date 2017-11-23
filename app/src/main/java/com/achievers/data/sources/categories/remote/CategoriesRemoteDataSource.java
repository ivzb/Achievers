package com.achievers.data.sources.categories.remote;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.BaseCallback;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.endpoints.CategoriesAPI;
import com.achievers.data.entities.Category;
import com.achievers.data.sources.RESTClient;
import com.achievers.data.sources.categories.CategoriesDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of remote network data source.
 */
public class CategoriesRemoteDataSource implements CategoriesDataSource {

    private static final String GeneralErrorMessage = "Error occurred. Please try again.";

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

//    @Override
//    public void load(final Integer parentId, @NonNull final LoadCallback<Category> callback) {
//        this.apiService
//            .loadChildren(String.valueOf(parentId))
//            .enqueue(new Callback<List<Category>>() {
//                @Override
//                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
//                    processResponse(response, callback);
//                }
//
//                @Override
//                public void onFailure(Call<List<Category>> call, Throwable t) {
//                    callback.onFailure("Server could not be reached. Please try again.");
//                }
//            });
//    }


    @Override
    public void load(final Long parentId) {
        this.apiService
            .loadChildren(String.valueOf(parentId))
            .enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    //processResponse(response, callback);
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {
                    //callback.onFailure("Server could not be reached. Please try again.");
                }
            });
    }

    @Override
    public void get(
            @NonNull final int id,
            @NonNull final GetCallback<Category> callback) {

        final Call<Category> call = this.apiService.getCategory(id);

        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                processResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                callback.onFailure("Server could not be reached. Please try again.");
            }
        });
    }

    @Override
    public void save(@NonNull final List<Category> categories) {
        // saving categories to remote data source should not be possible
    }

    private <T> void processResponse(
            final Response<T> response,
            final BaseCallback<T> callback) {

        int statusCode = response.code();

        if (statusCode != 200) {
            callback.onFailure(GeneralErrorMessage);
            return;
        }

        callback.onSuccess(response.body());
    }
}