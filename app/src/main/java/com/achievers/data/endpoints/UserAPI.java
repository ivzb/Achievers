package com.achievers.data.endpoints;

import com.achievers.data.entities.Auth;
import com.achievers.data.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {

    @POST("user/auth")
    Call<String> auth(@Body Auth auth);

    @POST("user/create")
    Call<String> create(@Body User user);
}