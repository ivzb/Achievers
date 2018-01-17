package com.achievers.data.endpoints;

import com.achievers.BuildConfig;
import com.achievers.data.Result;
import com.achievers.data.entities.Achievement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AchievementsAPI {

    @GET(BuildConfig.API_VERSION + "/achievements")
    Call<Result<List<Achievement>>> load(@Query("page") int page);

    @GET(BuildConfig.API_VERSION + "/achievements/quest?id={id}&page={page}")
    Call<Result<List<Achievement>>> loadByQuest(
            @Path("id") String id,
            @Path("page") int page
    );

    @GET(BuildConfig.API_VERSION + "/achievement/{id}")
    Call<Result<Achievement>> get(@Path("id") String id);

    @POST(BuildConfig.API_VERSION + "/achievement/create")
    Call<Result<String>> create(@Body Achievement achievement);
}