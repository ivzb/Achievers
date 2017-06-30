package com.achievers.data.source.endpoints;

import com.achievers.data.Achievement;
import com.achievers.data.source.remote.ODataResponseArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AchievementsAPI {

    @GET("Achievements/LoadByCategory/{id}")
    Call<ODataResponseArray<Achievement>> loadByCategory(
            @Path("id") int id
    );

    @GET("Achievements/Details/{id}")
    Call<Achievement> getAchievement(
            @Path("id") int id
    );
}