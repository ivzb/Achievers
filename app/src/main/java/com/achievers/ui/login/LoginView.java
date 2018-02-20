package com.achievers.ui.login;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.databinding.LoginFragBinding;
import com.achievers.ui._base.AbstractView;
import com.achievers.ui._base.views.EndlessScrollView;
import com.achievers.utils.ui.SwipeRefreshLayoutUtils;

public class LoginView
        extends AbstractView<LoginContract.Presenter, LoginContract.ViewModel, LoginFragBinding>
        implements LoginContract.View<LoginFragBinding> {

    private static final String USERNAME_STATE = "username_state";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.login_frag, container, false);

        mDataBinding = LoginFragBinding.bind(view);

        mDataBinding.setViewModel(mViewModel);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(USERNAME_STATE)) {
                String username = savedInstanceState.getString(USERNAME_STATE);
                mViewModel.setUsername(username);
            }
        }

        return mDataBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mViewModel != null) {
            outState.putString(USERNAME_STATE, mViewModel.getUsername());
        }
    }
}

