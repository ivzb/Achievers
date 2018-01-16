package com.achievers.data.sources.files;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.endpoints.FilesAPI;
import com.achievers.data.entities.File;
import com.achievers.data.sources.RESTClient;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilesRemoteDataSource implements FilesDataSource {

    private FilesAPI apiService;

    private static FilesDataSource sINSTANCE;

    public static FilesDataSource getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new FilesRemoteDataSource();
        }

        return sINSTANCE;
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    // Prevent direct instantiation.
    private FilesRemoteDataSource() {
        this.apiService = RESTClient
                .getClient()
                .create(FilesAPI.class);
    }

    @Override
    public void storeFile(@NonNull File file, final @NonNull SaveCallback<String> callback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(file.getContentType()), file.getContent());
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getContentType(), requestBody);

        final Call<String> call = apiService.storeFile(part);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int statusCode = response.code();

                if (statusCode != 201) {
                    String message = "Could not store File. Please try again.";

                    // todo: use web service error
                    callback.onFailure(message);

                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String message = "Could not store file. Please try again.";
                callback.onFailure(message);
            }
        });
    }
}