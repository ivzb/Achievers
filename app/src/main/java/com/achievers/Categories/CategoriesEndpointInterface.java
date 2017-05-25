package com.achievers.Categories;

import com.achievers.data.Category;
import com.achievers.data.source.remote.ODataResponseArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoriesEndpointInterface {
    @GET("Categories({id})")
    Call<Category> getCategory(@Path("id") int id);

    @GET("Categories")
    Call<ODataResponseArray<Category>> getCategories();

    @GET("RootCategoryChildren")
    Call<ODataResponseArray<Category>> getRootCategoryChildren();

    @GET("Categories({id})/Children")
    Call<ODataResponseArray<Category>> getChildren(@Path("id") int id);
}