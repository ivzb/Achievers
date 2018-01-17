package com.achievers.data.endpoints;

import com.achievers.BuildConfig;
import com.achievers.data.Result;
import com.achievers.data.entities.Reward;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RewardsAPI {

    @GET(BuildConfig.API_VERSION + "/rewards?page={page}")
    Call<Result<List<Reward>>> load(@Path("page") int page);

    @GET(BuildConfig.API_VERSION + "/rewards/{id}")
    Call<Result<Reward>> get(@Path("id") String id);
}
