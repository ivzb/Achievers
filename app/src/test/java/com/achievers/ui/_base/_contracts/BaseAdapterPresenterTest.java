package com.achievers.ui._base._contracts;

import android.content.Context;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.sources._base.contracts.ReceiveDataSource;

public interface BaseAdapterPresenterTest<M extends BaseModel, P extends BasePresenter, V extends BaseView, DS extends ReceiveDataSource> {

    Context getContext();
    V getView();
    DS getDataSource();

    M instantiateModel(Long id);
    P instantiatePresenter(Context context, V view, DS dataSource);
}
