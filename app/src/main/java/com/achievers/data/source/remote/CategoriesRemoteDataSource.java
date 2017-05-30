package com.achievers.data.source.remote;

import android.support.annotation.NonNull;

import com.achievers.Categories.CategoriesEndpointInterface;
import com.achievers.data.Category;
import com.achievers.data.source.CategoriesDataSource;
import com.achievers.data.source.callbacks.GetCallback;
import com.achievers.data.source.callbacks.LoadCallback;
import com.achievers.data.source.callbacks.SaveCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of remote network data source.
 */
public class CategoriesRemoteDataSource implements CategoriesDataSource {

    private static CategoriesRemoteDataSource INSTANCE;

    private CategoriesEndpointInterface apiService;

    public static CategoriesRemoteDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new CategoriesRemoteDataSource();
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private CategoriesRemoteDataSource() {
         this.apiService = RESTClient
                .getClient()
                .create(CategoriesEndpointInterface.class);
    }

    @Override
    public void loadCategories(
            final Integer parentId,
            final @NonNull LoadCallback<List<Category>> callback
    ) {
        final Call<ODataResponseArray<Category>> call =
                (parentId == null ?
                        this.apiService.loadRootCategoryChildren() :
                        this.apiService.loadChildren(parentId)
                );

        call.enqueue(new Callback<ODataResponseArray<Category>>() {
            @Override
            public void onResponse(Call<ODataResponseArray<Category>> call, Response<ODataResponseArray<Category>> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure("Error occurred. Please try again.");
                    return;
                }

                List<Category> categories = response.body().getResult();

                if (categories.isEmpty()) {
                    callback.onNoMoreData();
                    return;
                }

                callback.onSuccess(categories);
            }

            @Override
            public void onFailure(Call<ODataResponseArray<Category>> call, Throwable t) {
                callback.onFailure("Server could not be reached. Please try again.");
            }
        });
    }

    @Override
    public void getCategory(
            @NonNull final Integer categoryId,
            final @NonNull GetCallback<Category> callback
    ) {
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

    @Override
    public void saveCategories(
            @NonNull final List<Category> categories,
            @NonNull SaveCallback<Void> callback
    ) {
        // not being used yet
    }

    @Override
    public void refreshCache() {
        // Not required because the {@link CategoriesRepository} handles the logic of refreshing the
        // Categories from all the available data sources.
    }
}