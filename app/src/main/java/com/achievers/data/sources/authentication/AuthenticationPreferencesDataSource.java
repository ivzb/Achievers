package com.achievers.data.sources.authentication;

import com.achievers.DefaultConfig;
import com.achievers.R;
import com.achievers.utils.SharedPreferencesUtils;

public class AuthenticationPreferencesDataSource
        implements AuthenticationDataSource {

    private static AuthenticationDataSource sINSTANCE;

    public static AuthenticationDataSource getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new AuthenticationPreferencesDataSource();
        }

        return sINSTANCE;
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    private AuthenticationPreferencesDataSource() {

    }

    @Override
    public String getAuthToken() {
        return SharedPreferencesUtils.getValue(
                R.string.auth_token,
                DefaultConfig.NO_ID);
    }

    @Override
    public void setAuthToken(String authToken) {
        SharedPreferencesUtils.setValue(
                R.string.auth_token,
                authToken);
    }
}