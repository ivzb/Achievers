package com.achievers.data.endpoints;

import com.achievers.data.entities.Reward;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RewardsAPI {

    @GET("rewards?page={page}")
    Call<List<Reward>> load(@Path("page") int page);

    @GET("rewards/{id}")
    Call<Reward> get(@Path("id") String id);
}
