package com.achievers.data.endpoints;

import com.achievers.BuildConfig;
import com.achievers.data.Result;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FilesAPI {

    @Multipart
    @POST(BuildConfig.API_VERSION + "/File")
    Call<Result<String>> storeFile(@Part MultipartBody.Part image);
}
