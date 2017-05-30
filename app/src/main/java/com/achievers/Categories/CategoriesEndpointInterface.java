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
    Call<ODataResponseArray<Category>> loadCategories();

    @GET("RootCategoryChildren")
    Call<ODataResponseArray<Category>> loadRootCategoryChildren();

    @GET("Categories({id})/Children")
    Call<ODataResponseArray<Category>> loadChildren(@Path("id") int id);
}