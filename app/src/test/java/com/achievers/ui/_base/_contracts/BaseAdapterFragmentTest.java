package com.achievers.ui._base._contracts;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.ui._base._contracts.presenters.BaseEndlessAdapterPresenter;
import com.achievers.ui._base._contracts.view_models.BaseEndlessAdapterViewModel;
import com.achievers.ui._base._contracts.views.BaseEndlessAdapterView;

public interface BaseAdapterFragmentTest<M extends BaseModel, F extends BaseEndlessAdapterView, P extends BaseEndlessAdapterPresenter, VM extends BaseEndlessAdapterViewModel> {

//    Context getContext();
    F getFragment();
    P getPresenter();
    VM getViewModel();

    M instantiateModel(Long id);
}
