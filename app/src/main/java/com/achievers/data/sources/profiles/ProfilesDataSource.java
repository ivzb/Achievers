package com.achievers.data.sources.profiles;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities.Profile;
import com.achievers.data.sources._base.contracts.GetDataSource;

public interface ProfilesDataSource extends GetDataSource<Profile> {

    void me(GetCallback<Profile> callback);
}