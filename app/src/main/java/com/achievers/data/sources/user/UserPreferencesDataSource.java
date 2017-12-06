package com.achievers.data.sources.user;

import com.achievers.DefaultConfig;
import com.achievers.R;
import com.achievers.utils.SharedPreferencesUtils;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class UserPreferencesDataSource implements UserDataSource {

    private static UserPreferencesDataSource sINSTANCE;

    public static UserPreferencesDataSource getInstance() {
        checkNotNull(sINSTANCE);

        return sINSTANCE;
    }

    public static UserPreferencesDataSource createInstance() {
        sINSTANCE = new UserPreferencesDataSource();

        return getInstance();
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }


    private UserPreferencesDataSource() {

    }

    @Override
    public String getAuthToken() {
        return SharedPreferencesUtils.getValue(
                R.string.auth_token,
                DefaultConfig.String);
    }

    @Override
    public void setAuthToken(String authToken) {
        SharedPreferencesUtils.setValue(
                R.string.auth_token,
                authToken);
    }
}