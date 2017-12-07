package com.achievers.data.sources.authentication;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.endpoints.AuthenticationAPI;
import com.achievers.data.entities.Authentication;
import com.achievers.data.sources.RESTClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.achievers.utils.Preconditions.checkNotNull;

public class AuthenticationRemoteDataSource implements AuthenticationDataSource {

    private static AuthenticationRemoteDataSource sINSTANCE;

    private AuthenticationAPI apiService;

    public static AuthenticationRemoteDataSource getInstance() {
        checkNotNull(sINSTANCE);

        return sINSTANCE;
    }

    public static AuthenticationRemoteDataSource createInstance() {
        sINSTANCE = new AuthenticationRemoteDataSource();

        return getInstance();
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    // Prevent direct instantiation.
    private AuthenticationRemoteDataSource() {
        this.apiService = RESTClient
                .getClient()
                .create(AuthenticationAPI.class);
    }

    @Override
    public void auth(
            final String email,
            final String password,
            final @NonNull GetCallback<Authentication> callback) {

        final Call<Authentication> call = apiService.auth(email, password);

        call.enqueue(new Callback<Authentication>() {
            @Override
            public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                int statusCode = response.code();

                if (statusCode != 200) {
                    callback.onFailure("Could not authenticate. Please try again.");
                    return;
                }

                Authentication authentication = response.body();
                callback.onSuccess(authentication);
            }

            @Override
            public void onFailure(Call<Authentication> call, Throwable t) {
                callback.onFailure("Server could not be reached. Please try again.");
            }
        });
    }
}