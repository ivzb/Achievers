package com.achievers.Achievements;

import com.achievers.data.Achievement;
import com.achievers.data.source.remote.ODataResponseArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AchievementsEndpointInterface {
    @GET("Categories({id})/Achievements")
    Call<ODataResponseArray<Achievement>> getAchievements(@Path("id") int id);

    @GET("Achievements({id})")
    Call<Achievement> getAchievement(@Path("id") int id);
}
