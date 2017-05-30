package com.achievers.Evidence;

import com.achievers.data.Evidence;
import com.achievers.data.source.remote.ODataResponseArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EvidenceEndpointInterface {
    @GET("Achievements({id})/Evidence?$orderby=Id desc")
    Call<ODataResponseArray<Evidence>> loadEvidence(
            @Path("id") int id,
            @Query("$top") int top,
            @Query("$skip") int skip
    );

    @GET("Evidence({id})")
    Call<Evidence> getEvidence(
            @Path("id") int id
    );
}