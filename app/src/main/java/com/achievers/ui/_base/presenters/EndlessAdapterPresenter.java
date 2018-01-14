package com.achievers.ui._base.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.sources._base.contracts.ReceiveDataSource;
import com.achievers.ui._base.AbstractPresenter;
import com.achievers.ui._base._contracts.presenters.BaseEndlessAdapterPresenter;
import com.achievers.ui._base._contracts.views.BaseEndlessAdapterView;

import java.util.List;

import static com.achievers.DefaultConfig.PAGE;
import static com.achievers.utils.Preconditions.checkNotNull;

public abstract class EndlessAdapterPresenter<M extends BaseModel, V extends BaseEndlessAdapterView, DS extends ReceiveDataSource<M>>
        extends AbstractPresenter<V>
        implements BaseEndlessAdapterPresenter<M> {

    protected final DS mDataSource;

    public EndlessAdapterPresenter(
            @NonNull Context context,
            @NonNull V view,
            @NonNull DS dataSource) {

        mContext = checkNotNull(context, "context cannot be null");
        mView = checkNotNull(view, "view cannot be null");
        mDataSource = checkNotNull(dataSource, "achievementsDataSource cannot be null");
    }

    @Override
    public void start() {

    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void refresh(String id) {
        mView.clear();
        mView.setMore(true);
        load(id, PAGE);
    }

    @Override
    public void load(String id, final int page) {
        if (!mView.isActive()) return;

        mView.setLoadingIndicator(true);
        mView.setMore(true);

        mDataSource.load(id, page, mLoadCallback);
    }

    @Override
    public void click(M entity) {
        if (!mView.isActive()) return;

        if (entity == null) {
            mView.showErrorMessage("Missing achievement");
            return;
        }

        mView.openUi(entity);
    }

    protected LoadCallback<M> mLoadCallback = new LoadCallback<M>() {
        @Override
        public void onSuccess(List<M> entities, int page) {
            if (!mView.isActive()) return;

            mView.setPage(page);
            mView.setLoadingIndicator(false);
            mView.show(entities);
        }

        @Override
        public void onNoMore() {
            if (!mView.isActive()) return;

            mView.setLoadingIndicator(false);
            mView.setMore(false);
        }

        @Override
        public void onFailure(String message) {
            if (!mView.isActive()) return;

            mView.setLoadingIndicator(false);
            mView.setMore(false);
            mView.showErrorMessage(message);
        }
    };
}
