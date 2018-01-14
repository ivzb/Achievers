package com.achievers.data.sources.user;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Auth;
import com.achievers.data.entities.User;

public interface UserDataSource {

    void auth(
            Auth auth,
            SaveCallback<String> callback);

    void create(
            User user,
            SaveCallback<String> callback);

}