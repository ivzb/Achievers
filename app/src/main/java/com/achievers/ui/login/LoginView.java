package com.achievers.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.databinding.LoginFragBinding;
import com.achievers.ui._base.AbstractView;
import com.achievers.ui.home.HomeActivity;

public class LoginView
        extends AbstractView<LoginContract.Presenter, LoginContract.ViewModel, LoginFragBinding>
        implements LoginContract.View<LoginFragBinding> {

    private static final String EMAIL_STATE = "email_state";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.login_frag, container, false);

        mDataBinding = LoginFragBinding.bind(view);

        mDataBinding.setViewModel(mViewModel);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(EMAIL_STATE)) {
                String email = savedInstanceState.getString(EMAIL_STATE);
                mViewModel.setEmail(email);
            }
        }

        mDataBinding.btnLogin.setOnClickListener(mLoginListener);

        return mDataBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mViewModel != null) {
            outState.putString(EMAIL_STATE, mViewModel.getEmail());
        }
    }

    @Override
    public void showLoading(boolean loading) {
        if (!isActive()) return;

        mDataBinding.pbLoading.setVisibility(loading ? View.VISIBLE : View.GONE);
        mDataBinding.btnLogin.setVisibility(loading ? View.GONE : View.VISIBLE);
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private View.OnClickListener mLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mPresenter.auth(
                    mViewModel.getEmail(),
                    mViewModel.getPassword());
        }
    };
}

