package com.achievers.data.source.endpoints;

import com.achievers.data.Evidence;
import com.achievers.data.source.remote.ODataResponseArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EvidenceAPI {

    @GET("Evidence/LoadByAchievement/{id}")
    Call<ODataResponseArray<Evidence>> loadByAchievement(
            @Path("id") int id
    );

    @GET("Evidence/Details/{id}")
    Call<Evidence> getEvidence(
            @Path("id") int id
    );
}