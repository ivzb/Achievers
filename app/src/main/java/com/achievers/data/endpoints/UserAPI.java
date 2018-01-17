package com.achievers.data.endpoints;

import com.achievers.BuildConfig;
import com.achievers.data.Result;
import com.achievers.data.entities.Auth;
import com.achievers.data.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {

    @POST(BuildConfig.API_VERSION + "/user/auth")
    Call<Result<String>> auth(@Body Auth auth);

    @POST(BuildConfig.API_VERSION + "/user/create")
    Call<Result<String>> create(@Body User user);
}