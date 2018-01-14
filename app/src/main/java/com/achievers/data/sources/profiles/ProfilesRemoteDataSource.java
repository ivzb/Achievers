package com.achievers.data.sources.profiles;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.endpoints.ProfilesAPI;
import com.achievers.data.entities.Profile;
import com.achievers.data.sources._base.BaseRemoteDataSource;

import retrofit2.Call;

public class ProfilesRemoteDataSource
    extends BaseRemoteDataSource<Profile, ProfilesAPI>
    implements ProfilesDataSource {

    private static ProfilesDataSource sINSTANCE;

    public static ProfilesDataSource getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new ProfilesRemoteDataSource();
        }

        return sINSTANCE;
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    // Prevent direct instantiation.
    private ProfilesRemoteDataSource() {
        super(ProfilesAPI.class);
    }


    @Override
    public void me(
            GetCallback<Profile> callback) {

        final Call<Profile> call = mApiService.me();
        call.enqueue(getCallback(callback));
    }

    @Override
    public void get(
            String id,
            GetCallback<Profile> callback) {

        final Call<Profile> call = mApiService.get(id);
        call.enqueue(getCallback(callback));
    }
}
