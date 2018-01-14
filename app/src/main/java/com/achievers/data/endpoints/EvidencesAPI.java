package com.achievers.data.endpoints;

import com.achievers.data.entities.Evidence;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EvidencesAPI {

    @GET("evidences?page={page}")
    Call<List<Evidence>> load(@Path("page") int page);

    @GET("evidence/{id}")
    Call<Evidence> get(@Path("id") String id);

    @POST("evidence/create")
    Call<String> create(@Body Evidence evidence);
}
