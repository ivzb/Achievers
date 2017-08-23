package com.achievers.data.endpoints;

import com.achievers.entities.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoriesAPI {

    @GET("Categories/Details/{id}")
    Call<Category> getCategory(@Path("id") int id);

    @GET("Categories/Children/{parentId}")
    Call<List<Category>> loadChildren(@Path("parentId") String parentId);
}