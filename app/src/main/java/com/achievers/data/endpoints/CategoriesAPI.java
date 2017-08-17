package com.achievers.data.endpoints;

import com.achievers.entities.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoriesAPI {

    @GET("Categories/Details/{id}")
    Call<Category> getCategory(
            @Path("id") int id
    );

    @GET("Categories/Get/1")
    Call<List<Category>> loadCategories();

//    @GET("Categories/Children/null")
//    Call<List<Category>> loadRootChildren();
//
//    @GET("Categories/Children/{id}")
//    Call<List<Category>> loadChildren(
//            @Path("id") Integer id
//    );
}