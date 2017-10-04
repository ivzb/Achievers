package com.achievers.ui._base.contracts;

public interface BaseView<P extends BasePresenter, VM extends BaseViewModel> {

    void setPresenter(P presenter);
    void setViewModel(VM viewModel);

    void showSuccessfulMessage(String message);
    void showErrorMessage(String message);

    void hideKeyboard();

    boolean isActive();
}