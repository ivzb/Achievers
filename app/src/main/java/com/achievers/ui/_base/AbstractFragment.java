package com.achievers.ui._base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;
import com.achievers.utils.KeyboardUtils;

public abstract class AbstractFragment<P extends BasePresenter, VM extends BaseViewModel, DB extends ViewDataBinding>
        extends Fragment
        implements BaseView<P, VM, DB> {

    protected P mPresenter;
    protected VM mViewModel;
    protected DB mDataBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        setRetainInstance(true);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

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

        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }
}