package com.achievers.ui._base._contracts.views;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v4.widget.SwipeRefreshLayout;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.ui._base._contracts.BasePresenter;
import com.achievers.ui._base._contracts.BaseView;
import com.achievers.ui._base._contracts.view_models.BaseEndlessAdapterViewModel;

import java.util.List;

public interface BaseEndlessAdapterView<M extends BaseModel, P extends BasePresenter, VM extends BaseEndlessAdapterViewModel<? extends BaseModel>, DB extends ViewDataBinding>
        extends BaseView<P, VM, DB>, SwipeRefreshLayout.OnRefreshListener {

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void setLoadingIndicator(boolean active);

    void show(List<M> entities);

    void openUi(M entity);

    int getPage();
    void setPage(int page);
}
