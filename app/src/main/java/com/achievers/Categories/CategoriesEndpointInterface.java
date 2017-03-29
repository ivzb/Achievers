package com.achievers.Categories;

import com.achievers.data.Category;
import com.achievers.data.source.remote.ODataResponseArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriesEndpointInterface {
    @GET("http://host:port/odata/Categories")
    Call<ODataResponseArray<Category>> getCategories();
}