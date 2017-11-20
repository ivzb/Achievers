package com.achievers.ui._base;

import android.content.Context;

import com.achievers.ui._base._contracts.BaseView;

public abstract class AbstractPresenter<V extends BaseView> {

    protected Context mContext;
    protected V mView;
}