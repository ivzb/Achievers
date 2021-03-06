package com.achievers.ui.quests;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Quest;
import com.achievers.data.sources.DataSources;
import com.achievers.databinding.QuestsFragBinding;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base.views.EndlessScrollView;
import com.achievers.ui.quest.QuestActivity;
import com.achievers.ui.quests.adapters.QuestsAdapter;
import com.achievers.utils.ui.SwipeRefreshLayoutUtils;

public class QuestsView
        extends EndlessScrollView<Quest, QuestsContract.Presenter, QuestsContract.ViewModel, QuestsFragBinding>
        implements QuestsContract.View<QuestsFragBinding>,
                   BaseAdapterActionHandler<Quest>,
                   SwipeRefreshLayout.OnRefreshListener {

    private static final String QUESTS_STATE = "quests_state";
    private static final String LAYOUT_MANAGER_STATE = "layout_manager_state";
    private static final String PAGE_STATE = "page_state";

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
                    DataSources.getInstance().getQuests());
        }

        mDataBinding.setViewModel(mViewModel);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(PAGE_STATE)) {
                int page = savedInstanceState.getInt(PAGE_STATE);
                setPage(page);
            }
        }

        super.setUpRecycler(
                getContext(),
                new QuestsAdapter(getContext(), this),
                mDataBinding.rvQuests);

        SwipeRefreshLayoutUtils.setup(
                getContext(),
                mDataBinding.refreshLayout,
                mDataBinding.rvQuests,
                this);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(QUESTS_STATE)) {
                Parcelable achievementsState = savedInstanceState.getParcelable(QUESTS_STATE);
                mViewModel.getAdapter().onRestoreInstanceState(achievementsState);
            }

            if (savedInstanceState.containsKey(LAYOUT_MANAGER_STATE)) {
                Parcelable layoutManagerState = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE);
                mDataBinding.rvQuests.getLayoutManager().onRestoreInstanceState(layoutManagerState);
            }
        } else {
            mPresenter.refresh(mViewModel.getContainerId());
        }

        return mDataBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mViewModel != null) {
            outState.putInt(PAGE_STATE, mViewModel.getPage());

            if (mViewModel.getAdapter() != null){
                Parcelable achievementsState = mViewModel.getAdapter().onSaveInstanceState();
                outState.putParcelable(QUESTS_STATE, achievementsState);
            }
        }

        if (mDataBinding.rvQuests != null && mDataBinding.rvQuests.getLayoutManager() != null) {
            Parcelable layoutManagerState = mDataBinding.rvQuests.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(LAYOUT_MANAGER_STATE, layoutManagerState);
        }
    }

    @Override
    public void openUi(Quest quest) {
        Intent intent = new Intent(getContext(), QuestActivity.class);
        intent.putExtra(QuestActivity.EXTRA_QUEST_ID, quest.getId());
        startActivity(intent);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (!isActive()) return;

        SwipeRefreshLayoutUtils.setLoading(mDataBinding.refreshLayout, active);
    }

    @Override
    public void onAdapterEntityClick(Quest quest) {
        mPresenter.click(quest);
    }
}