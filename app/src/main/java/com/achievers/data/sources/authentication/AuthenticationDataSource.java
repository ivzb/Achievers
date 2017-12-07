package com.achievers.data.sources.authentication;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities.Authentication;

public interface AuthenticationDataSource {

    void auth(String email, String password, final GetCallback<Authentication> callback);
}