package com.achievers.ui._base._contracts;

import android.databinding.ViewDataBinding;

public interface BaseView<P extends BasePresenter, VM extends BaseViewModel, DB extends ViewDataBinding> {

    void setPresenter(P presenter);
    void setViewModel(VM viewModel);

    void showSuccessfulMessage(String message);
    void showErrorMessage(String message);

    void hideKeyboard();

    boolean isActive();
}