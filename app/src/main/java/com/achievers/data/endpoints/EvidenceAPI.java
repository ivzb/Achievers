package com.achievers.data.endpoints;

import com.achievers.data.entities.Evidence;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EvidenceAPI {

    @GET("Evidence/Details/{id}")
    Call<Evidence> getEvidence(
            @Path("id") String id
    );

    @GET("Evidence/LoadByAchievement/{id}")
    Call<List<Evidence>> loadByAchievement(
            @Path("id") String id
    );
}