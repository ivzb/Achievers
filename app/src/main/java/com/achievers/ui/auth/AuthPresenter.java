package com.achievers.ui.auth;

import android.content.Context;
import android.support.annotation.NonNull;

import com.achievers.data.Result;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Auth;
import com.achievers.data.entities.User;
import com.achievers.data.sources.user.UserDataSource;
import com.achievers.ui._base.AbstractPresenter;

import static com.achievers.utils.Preconditions.checkNotNull;

public class AuthPresenter
        extends AbstractPresenter<AuthContract.View>
        implements AuthContract.Presenter {

    private final UserDataSource mUserDataSource;

    AuthPresenter(
            @NonNull Context context,
            @NonNull AuthContract.View view,
            @NonNull UserDataSource userDataSource) {

        mContext = checkNotNull(context, "context cannot be null");
        mView = checkNotNull(view, "view cannot be null");
        mUserDataSource = checkNotNull(userDataSource, "userDataSource cannot be null");
    }

    @Override
    public void start() {

    }

    @Override
    public void login(String email, String password) {
        mView.showLoading(true);

        // todo: add validation

        Auth auth = new Auth(email, password);

        mUserDataSource.auth(auth, new SaveCallback<String>() {
            @Override
            public void onSuccess(Result<String> data) {
                if (!mView.isActive()) return;

                mView.navigateToHome();
                mView.showLoading(false);
            }

            @Override
            public void onFailure(String message) {
                if (!mView.isActive()) return;

                mView.showLoading(false);
                mView.showErrorMessage(message);
            }
        });
    }

    @Override
    public void register(String email, String password) {
        mView.showLoading(true);

        // todo: add validation

        // todo: replace user with auth model
        User user = new User(email, password);

        mUserDataSource.create(user, new SaveCallback<String>() {
            @Override
            public void onSuccess(Result<String> data) {
                if (!mView.isActive()) return;

                mView.navigateToHome();
                mView.showLoading(false);
            }

            @Override
            public void onFailure(String message) {
                if (!mView.isActive()) return;

                mView.showLoading(false);
                mView.showErrorMessage(message);
            }
        });
    }
}
