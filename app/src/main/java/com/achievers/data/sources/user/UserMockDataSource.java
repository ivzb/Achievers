package com.achievers.data.sources.user;

import com.achievers.data.Result;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Auth;
import com.achievers.data.entities.User;
import com.achievers.data.generators._base.contracts.BaseGeneratorConfig;
import com.achievers.data.generators.config.GeneratorConfig;

import static com.achievers.utils.Preconditions.checkNotNull;

public class UserMockDataSource
        implements UserDataSource {

    private static UserMockDataSource sINSTANCE;
    private BaseGeneratorConfig mConfig;

    public static UserMockDataSource getInstance() {
        checkNotNull(sINSTANCE);

        return sINSTANCE;
    }

    public static UserMockDataSource createInstance() {
        sINSTANCE = new UserMockDataSource();

        return getInstance();
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    private UserMockDataSource() {
        mConfig = GeneratorConfig.getInstance();
    }

    @Override
    public void auth(
            Auth auth,
            SaveCallback<String> callback) {

        checkNotNull(callback);

        boolean emailMatches = mConfig.getEmail().equals(auth.getEmail());
        boolean passwordMatches = mConfig.getPassword().equals(auth.getPassword());

        if (emailMatches && passwordMatches) {
            String token = mConfig.getAuthenticationToken();
            Result<String> result = new Result<>(token);
            callback.onSuccess(result);
            return;
        }

        callback.onFailure("Wrong email or password.");
    }

    @Override
    public void create(User user, SaveCallback<String> callback) {
        // todo
    }
}