package com.achievers.ui.login;

import android.databinding.Bindable;
import android.databinding.ViewDataBinding;

import com.achievers.ui._base._contracts.BasePresenter;
import com.achievers.ui._base._contracts.BaseView;
import com.achievers.ui._base._contracts.BaseViewModel;

public class LoginContract {

    public interface View<DB extends ViewDataBinding>
            extends BaseView<Presenter, ViewModel, DB> {

    }

    public interface Presenter extends BasePresenter {

    }

    public interface ViewModel extends BaseViewModel {

        @Bindable
        String getUsername();
        void setUsername(String username);

        @Bindable
        String getPassword();
        void setPassword(String password);
    }
}

