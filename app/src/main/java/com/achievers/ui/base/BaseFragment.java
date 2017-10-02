package com.achievers.ui.base;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.achievers.ui.base.contracts.BasePresenter;
import com.achievers.ui.base.contracts.BaseView;
import com.achievers.utils.KeyboardUtils;

public abstract class BaseFragment<P extends BasePresenter, VM>
        extends Fragment
        implements BaseView<P, VM> {

    protected P mPresenter;
    protected VM mViewModel;

    @Override
    public void setPresenter(P presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setViewModel(VM viewModel) {
        mViewModel = viewModel;
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