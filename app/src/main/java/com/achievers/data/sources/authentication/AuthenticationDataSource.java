package com.achievers.data.sources.authentication;

public interface AuthenticationDataSource {

    String getAuthToken();
    void setAuthToken(String authToken);
}