package com.achievers.ui._base.views;

import android.content.Intent;
import android.databinding.ViewDataBinding;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.ui._base.AbstractView;
import com.achievers.ui._base._contracts.presenters.BaseEndlessAdapterPresenter;
import com.achievers.ui._base._contracts.view_models.BaseEndlessAdapterViewModel;
import com.achievers.ui._base._contracts.views.BaseEndlessAdapterView;

import java.util.List;

public abstract class EndlessAdapterView<M extends BaseModel, P extends BaseEndlessAdapterPresenter<M>, VM extends BaseEndlessAdapterViewModel<M>, DB extends ViewDataBinding>
        extends AbstractView<P, VM, DB>
        implements BaseEndlessAdapterView<M, P, VM, DB> {

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void show(List<M> entities) {
        mViewModel.getAdapter().add(entities);
    }

    @Override
    public int getPage() {
        return mViewModel.getPage();
    }

    @Override
    public void setPage(int page) {
        mViewModel.setPage(page);
    }

}
