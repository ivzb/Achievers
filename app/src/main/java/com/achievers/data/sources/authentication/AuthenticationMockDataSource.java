package com.achievers.data.sources.authentication;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities.Authentication;
import com.achievers.data.generators._base.contracts.BaseGeneratorConfig;
import com.achievers.data.generators.config.GeneratorConfig;

import static com.achievers.utils.Preconditions.checkNotNull;

public class AuthenticationMockDataSource
        implements AuthenticationDataSource {

    private static AuthenticationMockDataSource sINSTANCE;
    private BaseGeneratorConfig mConfig;

    public static AuthenticationMockDataSource getInstance() {
        checkNotNull(sINSTANCE);

        return sINSTANCE;
    }

    public static AuthenticationMockDataSource createInstance() {
        sINSTANCE = new AuthenticationMockDataSource();

        return getInstance();
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    private AuthenticationMockDataSource() {
        mConfig = GeneratorConfig.getInstance();
    }

    @Override
    public void auth(
            String email,
            String password,
            GetCallback<Authentication> callback) {

        checkNotNull(callback);

        boolean emailMatches = mConfig.getEmail().equals(email);
        boolean passwordMatches = mConfig.getPassword().equals(password);

        if (emailMatches && passwordMatches) {
            Authentication authentication = new Authentication(
                    mConfig.getAuthenticationToken());

            callback.onSuccess(authentication);
            return;
        }

        callback.onFailure("Authentication failure.");
    }
}