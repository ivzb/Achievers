package com.achievers.ui.quests;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Quest;
import com.achievers.data.source.quests.QuestsMockDataSource;
import com.achievers.databinding.QuestsFragBinding;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base.views.EndlessAdapterView;

public class QuestsView
        extends EndlessAdapterView<Quest, QuestsContract.Presenter, QuestsContract.ViewModel, QuestsFragBinding>
        implements QuestsContract.View<QuestsFragBinding>,
                   BaseAdapterActionHandler<Quest>,
                   SwipeRefreshLayout.OnRefreshListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.quests_frag, container, false);

        mDataBinding = QuestsFragBinding.bind(view);

        if (mViewModel == null) {
            mViewModel = new QuestsViewModel();
        }

        if (mPresenter == null) {
            mPresenter = new QuestsPresenter(
                    getContext(),
                    this,
                    QuestsMockDataSource.getInstance());
        }

        mDataBinding.setViewModel(mViewModel);

        return view;
    }

    @Override
    public void openUi(Quest quest) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void onAdapterEntityClick(Quest quest) {
        mPresenter.click(quest);
    }


    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }
}