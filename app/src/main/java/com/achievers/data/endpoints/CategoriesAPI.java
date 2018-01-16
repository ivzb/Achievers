package com.achievers.data.endpoints;

import com.achievers.BuildConfig;
import com.achievers.data.entities.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoriesAPI {

    @GET(BuildConfig.API_VERSION + "/Categories/Details/{id}")
    Call<Category> getCategory(@Path("id") int id);

    @GET(BuildConfig.API_VERSION + "/Categories/Children/{parentId}")
    Call<List<Category>> loadChildren(@Path("parentId") String parentId);
}