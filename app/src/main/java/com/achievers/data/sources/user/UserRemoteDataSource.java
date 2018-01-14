package com.achievers.data.sources.user;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.endpoints.UserAPI;
import com.achievers.data.entities.Auth;
import com.achievers.data.entities.User;
import com.achievers.data.sources.RESTClient;
import com.achievers.data.sources._base.BaseRemoteDataSource;

import retrofit2.Call;

public class UserRemoteDataSource
        extends BaseRemoteDataSource<Auth>
        implements UserDataSource {

    private static UserDataSource sINSTANCE;

    private UserAPI apiService;

    public static UserDataSource getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new UserRemoteDataSource();
        }

        return sINSTANCE;
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    // Prevent direct instantiation.
    private UserRemoteDataSource() {
        this.apiService = RESTClient
                .getClient()
                .create(UserAPI.class);
    }

    @Override
    public void auth(
            final @NonNull Auth auth,
            final @NonNull SaveCallback<String> callback) {

        final Call<String> call = apiService.auth(auth);
        call.enqueue(saveCallback(callback));
    }

    @Override
    public void create(
            final @NonNull User user,
            final @NonNull SaveCallback<String> callback) {

        final Call<String> call = apiService.create(user);
        call.enqueue(saveCallback(callback));
    }
}