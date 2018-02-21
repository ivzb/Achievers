package com.achievers.ui.login;

import android.databinding.Bindable;
import android.databinding.ViewDataBinding;

import com.achievers.ui._base._contracts.BasePresenter;
import com.achievers.ui._base._contracts.BaseView;
import com.achievers.ui._base._contracts.BaseViewModel;

public class LoginContract {

    public interface View<DB extends ViewDataBinding>
            extends BaseView<Presenter, ViewModel, DB> {

        void showLoading(boolean loading);
        void navigateToHome();
    }

    public interface Presenter extends BasePresenter {

        void auth(String email, String password);
    }

    public interface ViewModel extends BaseViewModel {

        @Bindable
        String getEmail();
        void setEmail(String email);

        @Bindable
        String getPassword();
        void setPassword(String password);
    }
}

