package com.achievers.data.source.files;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.endpoints.FilesAPI;
import com.achievers.data.entities.File;
import com.achievers.data.source.RESTClient;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilesRemoteDataSource implements FilesDataSource {

    private FilesAPI apiService;

    private static FilesDataSource INSTANCE;

    public static FilesDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FilesRemoteDataSource();
        }

        return INSTANCE;
    }

    // Prevent direct instantiation.
    private FilesRemoteDataSource() {
        this.apiService = RESTClient
                .getClient()
                .create(FilesAPI.class);
    }

    @Override
    public void storeFile(@NonNull File file, final @NonNull SaveCallback<File> callback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(file.getContentType()), file.getContent());
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getContentType(), requestBody);

        final Call<File> call = apiService.storeFile(part);

        call.enqueue(new Callback<File>() {
            @Override
            public void onResponse(Call<File> call, Response<File> response) {
                int statusCode = response.code();

                if (statusCode != 201) {
                    String message = "Could not store File. Please try again.";

                    try {
                        // todo: use web service error
                        message = response.errorBody().string().replaceAll("\"", "");
                    } catch (IOException e) {
                    } finally {
                        callback.onFailure(message);
                    }

                    return;
                }

                File savedFile = response.body();
                callback.onSuccess(savedFile);
            }

            @Override
            public void onFailure(Call<File> call, Throwable t) {
                String message = "Could not store file. Please try again.";
                callback.onFailure(message);
            }
        });
    }
}