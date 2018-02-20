package com.achievers.ui.login;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class LoginViewModel
        extends BaseObservable
        implements LoginContract.ViewModel {

    private String mUsername;
    private String mPassword;

    @Bindable
    @Override
    public String getUsername() {
        return mUsername;
    }

    @Override
    public void setUsername(String username) {
        mUsername = username;
    }

    @Bindable
    @Override
    public String getPassword() {
        return mPassword;
    }

    @Override
    public void setPassword(String password) {
        mPassword = password;
    }
}
