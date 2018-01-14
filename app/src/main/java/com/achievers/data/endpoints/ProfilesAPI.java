package com.achievers.data.endpoints;

import com.achievers.data.entities.Profile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProfilesAPI {

    @GET("profile/me")
    Call<Profile> me();

    @GET("profile/{id}")
    Call<Profile> get(@Path("id") String id);
}
