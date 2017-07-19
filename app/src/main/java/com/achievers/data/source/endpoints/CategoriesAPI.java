package com.achievers.data.source.endpoints;

import com.achievers.data.Category;
import com.achievers.data.source.remote.ODataResponseArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoriesAPI {

    @GET("Categories/Details/{id}")
    Call<Category> getCategory(
            @Path("id") int id
    );

    @GET("Categories/Children/null")
    Call<ODataResponseArray<Category>> loadRootChildren();

    @GET("Categories/Children/{id}")
    Call<ODataResponseArray<Category>> loadChildren(
            @Path("id") Integer id
    );
}