package com.achievers.ui.base;

public interface BaseView<P, VM> {

    void setPresenter(P presenter);
    void setViewModel(VM viewModel);

    void showSuccessfulMessage(String message);
    void showErrorMessage(String message);

    void hideKeyboard();

    boolean isActive();
}