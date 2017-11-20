package com.achievers.ui._base._contracts.presenters;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.ui._base._contracts.BasePresenter;

public interface BaseEndlessAdapterPresenter<M extends BaseModel>
        extends BasePresenter {

    void refresh();

    void load(int page);

    void click(M entity);

    void result(int requestCode, int resultCode);
}
