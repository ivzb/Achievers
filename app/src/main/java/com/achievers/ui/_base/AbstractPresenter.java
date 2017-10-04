package com.achievers.ui._base;

import android.content.Context;

import com.achievers.ui._base.contracts.BaseView;

public abstract class AbstractPresenter<V extends BaseView> {

    protected Context mContext;
    protected V mView;
}