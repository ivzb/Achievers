package com.achievers.ui.auth;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class AuthViewModel
        extends BaseObservable
        implements AuthContract.ViewModel {

    private String mEmail;
    private String mPassword;

    @Bindable
    @Override
    public String getEmail() {
        return mEmail;
    }

    @Override
    public void setEmail(String email) {
        mEmail = email;
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
