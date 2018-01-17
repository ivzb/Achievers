package com.achievers.data.endpoints;

import com.achievers.BuildConfig;
import com.achievers.data.Result;
import com.achievers.data.entities.Profile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProfilesAPI {

    @GET(BuildConfig.API_VERSION + "/profile/me")
    Call<Result<Profile>> me();

    @GET(BuildConfig.API_VERSION + "/profile/{id}")
    Call<Result<Profile>> get(@Path("id") String id);
}
