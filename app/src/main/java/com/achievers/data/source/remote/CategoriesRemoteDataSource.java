package com.achievers.data.source.remote;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.achievers.Categories.CategoriesEndpointInterface;
import com.achievers.data.Category;
import com.achievers.data.source.CategoriesDataSource;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.bloco.faker.Faker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of remote network data source.
 */
public class CategoriesRemoteDataSource implements CategoriesDataSource {

    private static CategoriesRemoteDataSource INSTANCE;
    private static final int SERVICE_LATENCY_IN_MILLIS = 500;

    // for developing purposes I am not fetching data from web service
    private final static Map<Integer, Category> CATEGORIES_SERVICE_DATA;

    static {
        CATEGORIES_SERVICE_DATA = new LinkedHashMap<>();
        generateCategories(15, new Faker());
    }

    private static void generateCategories(int count, Faker faker)
    {
        if (count == 0) return;

        Category newCategory = new Category(count--, faker.lorem.word(), faker.lorem.paragraph(), true, faker.date.backward());
        CATEGORIES_SERVICE_DATA.put(newCategory.getId(), newCategory);

        generateCategories(count, faker);
    }

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
        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onLoaded(Lists.newArrayList(CATEGORIES_SERVICE_DATA.values()));
            }
        }, SERVICE_LATENCY_IN_MILLIS);
        return;

//        CategoriesEndpointInterface apiService = RESTClient
//            .getClient()
//            .create(CategoriesEndpointInterface.class);
//
//        final Call<ODataResponseArray<Category>> call = apiService.getCategories();
//        call.enqueue(new Callback<ODataResponseArray<Category>>() {
//            @Override
//            public void onResponse(Call<ODataResponseArray<Category>> call, Response<ODataResponseArray<Category>> response) {
//                int statusCode = response.code();
//                List<Category> categories = response.body().getResult();
//                callback.onLoaded(categories);
//            }
//
//            @Override
//            public void onFailure(Call<ODataResponseArray<Category>> call, Throwable t) {
//                // Log error here since request failed
//                callback.onDataNotAvailable();
//            }
//        });
    }

    /**
     * Note: {@link GetCategoryCallback#onDataNotAvailable()} is fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void getCategory(@NonNull int categoryId, final @NonNull GetCategoryCallback callback) {
        final Category category = CATEGORIES_SERVICE_DATA.get(categoryId);

        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onLoaded(category);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void refreshCategories(List<Category> categories) {
        // Not required because the {@link CategoriesRepository} handles the logic of refreshing the
        // Categories from all the available data sources.
    }
}