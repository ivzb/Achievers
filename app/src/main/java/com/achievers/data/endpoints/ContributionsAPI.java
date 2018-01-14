package com.achievers.data.endpoints;

import com.achievers.data.entities.Contribution;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ContributionsAPI {

    @GET("contributions?page={page}")
    Call<List<Contribution>> load(@Path("page") int page);

    @GET("contribution/{id}")
    Call<Contribution> get(@Path("id") String id);
}
