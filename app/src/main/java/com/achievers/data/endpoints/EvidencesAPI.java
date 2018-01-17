package com.achievers.data.endpoints;

import com.achievers.BuildConfig;
import com.achievers.data.Result;
import com.achievers.data.entities.Evidence;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EvidencesAPI {

    @GET(BuildConfig.API_VERSION + "/evidences?page={page}")
    Call<Result<List<Evidence>>> load(@Path("page") int page);

    @GET(BuildConfig.API_VERSION + "/evidence/{id}")
    Call<Result<Evidence>> get(@Path("id") String id);

    @POST(BuildConfig.API_VERSION + "/evidence/create")
    Call<Result<String>> create(@Body Evidence evidence);
}
