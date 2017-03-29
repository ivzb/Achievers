package com.achievers.data.source.remote;

import android.support.annotation.NonNull;

import com.achievers.Categories.CategoriesEndpointInterface;
import com.achievers.data.Category;
import com.achievers.data.source.CategoriesDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of remote network data source.
 */
public class CategoriesRemoteDataSource implements CategoriesDataSource {

    private static CategoriesRemoteDataSource INSTANCE;

    public static CategoriesRemoteDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new CategoriesRemoteDataSource();
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private CategoriesRemoteDataSource() { }

    /**
     * Note: {@link LoadCategoriesCallback#onDataNotAvailable()} is fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void getCategories(final @NonNull LoadCategoriesCallback callback) {
        CategoriesEndpointInterface apiService = RESTClient
            .getClient()
            .create(CategoriesEndpointInterface.class);

        final Call<ODataResponseArray<Category>> call = apiService.getCategories();
        call.enqueue(new Callback<ODataResponseArray<Category>>() {
            @Override
            public void onResponse(Call<ODataResponseArray<Category>> call, Response<ODataResponseArray<Category>> response) {
                int statusCode = response.code();
                List<Category> categories = response.body().getResult();
                callback.onLoaded(categories);
            }

            @Override
            public void onFailure(Call<ODataResponseArray<Category>> call, Throwable t) {
                // Log error here since request failed
                callback.onDataNotAvailable();
            }
        });
    }

    /**
     * Note: {@link GetCategoryCallback#onDataNotAvailable()} is fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void getCategory(@NonNull int categoryId, final @NonNull GetCategoryCallback callback) {
        throw new UnsupportedOperationException("getCategory is not implemented yet!");
    }

    @Override
    public void refreshCategories(List<Category> categories) {
        // Not required because the {@link OperatorCardsRepository} handles the logic of refreshing the
        // Categories from all the available data sources.
    }
}