package com.achievers.ui.rewards;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Reward;
import com.achievers.databinding.RewardsFragBinding;
import com.achievers.ui._base.views.EndlessScrollView;
import com.achievers.ui.rewards.adapters.RewardsAdapter;
import com.achievers.utils.ui.SwipeRefreshLayoutUtils;

public class RewardsView
        extends EndlessScrollView<Reward, RewardsContract.Presenter, RewardsContract.ViewModel, RewardsFragBinding>
        implements RewardsContract.View<RewardsFragBinding>, SwipeRefreshLayout.OnRefreshListener {

    private static final String REWARDS_STATE = "rewards_state";
    private static final String LAYOUT_MANAGER_STATE = "layout_manager_state";
    private static final String PAGE_STATE = "page_state";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.rewards_frag, container, false);

        mDataBinding = RewardsFragBinding.bind(view);

        mDataBinding.setViewModel(mViewModel);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(PAGE_STATE)) {
                int page = savedInstanceState.getInt(PAGE_STATE);
                setPage(page);
            }
        }

        super.setUpRecycler(
                getContext(),
                new RewardsAdapter(getContext(), null),
                mDataBinding.rvRewards);

        SwipeRefreshLayoutUtils.setup(
                getContext(),
                mDataBinding.refreshLayout,
                mDataBinding.rvRewards,
                this);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(REWARDS_STATE)) {
                Parcelable achievementsState = savedInstanceState.getParcelable(REWARDS_STATE);
                mViewModel.getAdapter().onRestoreInstanceState(achievementsState);
            }

            if (savedInstanceState.containsKey(LAYOUT_MANAGER_STATE)) {
                Parcelable layoutManagerState = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE);
                mDataBinding.rvRewards.getLayoutManager().onRestoreInstanceState(layoutManagerState);
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
                outState.putParcelable(REWARDS_STATE, achievementsState);
            }
        }

        if (mDataBinding.rvRewards != null && mDataBinding.rvRewards.getLayoutManager() != null) {
            Parcelable layoutManagerState = mDataBinding.rvRewards.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(LAYOUT_MANAGER_STATE, layoutManagerState);
        }
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (!isActive()) return;

        SwipeRefreshLayoutUtils.setLoading(mDataBinding.refreshLayout, active);
    }
}