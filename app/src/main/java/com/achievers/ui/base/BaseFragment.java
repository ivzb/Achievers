package com.achievers.ui.base;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.achievers.utils.KeyboardUtils;

public abstract class BaseFragment<T> extends Fragment implements BaseView<T> {

    protected T mPresenter;

    @Override
    public void setPresenter(T presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtils.hideKeyboard(getActivity());
    }

    @Override
    public void showSuccessfulMessage(String message) {
        showMessage(message);
    }

    @Override
    public void showErrorMessage(String message) {
        showMessage(message);
    }

    private void showMessage(String message) {
        if (getView() == null || !isActive()) return;

        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }
}