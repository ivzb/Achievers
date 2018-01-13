package com.achievers.data.sources.user;

import com.achievers.DefaultConfig;
import com.achievers.R;
import com.achievers.utils.SharedPreferencesUtils;

public class UserPreferencesDataSource implements UserDataSource {

    private static UserPreferencesDataSource sINSTANCE;

    public static UserDataSource getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new UserPreferencesDataSource();
        }

        return sINSTANCE;
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