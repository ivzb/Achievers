package com.achievers.data.endpoints;

import com.achievers.data.entities.Authentication;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthenticationAPI {

    @FormUrlEncoded
    @POST("auth")
    Call<Authentication> auth(
        @Field("email") String email,
        @Field("Password") String password);
}