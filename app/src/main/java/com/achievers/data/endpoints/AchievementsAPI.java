package com.achievers.data.endpoints;

import com.achievers.data.entities.Achievement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AchievementsAPI {

    @GET("Achievements/ByCategory/{id}")
    Call<List<Achievement>> loadByCategory(
            @Path("id") long id
    );

    @GET("Achievements/Details/{id}")
    Call<Achievement> getAchievement(
            @Path("id") long id
    );
}