package com.achievers.ui._base.views;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.ui._base.AbstractView;
import com.achievers.ui._base._contracts.presenters.BaseEndlessAdapterPresenter;
import com.achievers.ui._base._contracts.view_models.BaseEndlessAdapterViewModel;
import com.achievers.ui._base._contracts.views.BaseEndlessAdapterView;
import com.achievers.ui._base.adapters.ActionHandlerAdapter;
import com.achievers.utils.ui.EndlessRecyclerViewScrollListener;

import java.util.List;

public abstract class EndlessScrollView<M extends BaseModel, P extends BaseEndlessAdapterPresenter<M>, VM extends BaseEndlessAdapterViewModel<M>, DB extends ViewDataBinding>
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
    public void openUi(M entity) {

    }

    @Override
    public void onRefresh() {
        mPresenter.refresh(mViewModel.getContainerId());
    }

    @Override
    public int getPage() {
        return mViewModel.getPage();
    }

    @Override
    public void setPage(int page) {
        mViewModel.setPage(page);
    }

    @Override
    public void setNoMore() {
        mViewModel.setNoMore();
    }

    @Override
    public RecyclerView.LayoutManager instantiateLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    protected void setUpRecycler(
            Context context,
            ActionHandlerAdapter<M> adapter,
            RecyclerView recyclerView) {

        mViewModel.setAdapter(adapter);
        LinearLayoutManager layoutManager = (LinearLayoutManager) instantiateLayoutManager(context);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(
                layoutManager,
                mViewModel.getPage()) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.load(mViewModel.getContainerId(), page);
            }
        });
    }
}