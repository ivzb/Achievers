package com.achievers.data.source.endpoints;

import com.achievers.data.Achievement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AchievementsAPI {

    @GET("Achievements/ByCategory/{id}")
    Call<List<Achievement>> loadByCategory(
            @Path("id") int id
    );

    @GET("Achievements/Details/{id}")
    Call<Achievement> getAchievement(
            @Path("id") int id
    );
}