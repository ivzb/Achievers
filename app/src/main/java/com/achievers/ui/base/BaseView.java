package com.achievers.ui.base;

public interface BaseView<T> {

    void setPresenter(T presenter);

    boolean isActive();

    void hideKeyboard();

    void showSuccessfulMessage(String message);

    void showErrorMessage(String message);
}