package com.achievers.data.endpoints;

import com.achievers.BuildConfig;
import com.achievers.data.entities.Quest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuestsAPI {

    @GET(BuildConfig.API_VERSION + "/quests?page={page}")
    Call<List<Quest>> load(@Path("page") int page);

    @GET(BuildConfig.API_VERSION + "/quests/{id}")
    Call<Quest> get(@Path("id") String id);
}
