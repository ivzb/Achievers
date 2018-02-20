package com.achievers.ui.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.achievers.ui._base.AbstractPresenter;

import static com.achievers.utils.Preconditions.checkNotNull;

public class LoginPresenter
        extends AbstractPresenter<LoginContract.View>
        implements LoginContract.Presenter {

    LoginPresenter(
            @NonNull Context context,
            @NonNull LoginContract.View view) {

        checkNotNull(context);
        checkNotNull(view);

        mContext = context;
        mView = view;
    }

    @Override
    public void start() {

    }
}
