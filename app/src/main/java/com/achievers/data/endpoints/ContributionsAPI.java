package com.achievers.data.endpoints;

import com.achievers.BuildConfig;
import com.achievers.data.entities.Contribution;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ContributionsAPI {

    @GET(BuildConfig.API_VERSION + "/contributions?page={page}")
    Call<List<Contribution>> load(@Path("page") int page);

    @GET(BuildConfig.API_VERSION + "/contribution/{id}")
    Call<Contribution> get(@Path("id") String id);
}
