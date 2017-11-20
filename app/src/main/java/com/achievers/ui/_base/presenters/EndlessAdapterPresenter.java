package com.achievers.ui._base.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.source._base.ReceiveDataSource;
import com.achievers.ui._base.AbstractPresenter;
import com.achievers.ui._base._contracts.presenters.BaseEndlessAdapterPresenter;
import com.achievers.ui._base._contracts.views.BaseEndlessAdapterView;

import java.util.List;

import static com.achievers.Config.RECYCLER_INITIAL_PAGE;
import static com.achievers.utils.Preconditions.checkNotNull;

public abstract class EndlessAdapterPresenter<M extends BaseModel, V extends BaseEndlessAdapterView, DS extends ReceiveDataSource<M>>
        extends AbstractPresenter<V>
        implements BaseEndlessAdapterPresenter<M> {

    private final DS mDataSource;

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
    public void refresh() {
        load(RECYCLER_INITIAL_PAGE);
    }

    @Override
    public void load(final int page) {
        if (!mView.isActive()) return;

        mView.setLoadingIndicator(true);

        mDataSource.load(null, page, new LoadCallback<M>() {
            @Override
            public void onSuccess(List<M> entities) {
                if (!mView.isActive()) return;

                mView.setPage(page);
                mView.setLoadingIndicator(false);
                mView.show(entities);
            }

            @Override
            public void onFailure(String message) {
                if (!mView.isActive()) return;

                mView.setLoadingIndicator(false);
                mView.showErrorMessage(message);
            }
        });
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
}