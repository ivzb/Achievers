package com.achievers.ui.contributions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.achievers.R;
import com.achievers.data.entities.AchievementProgress;
import com.achievers.data.sources.achievements_progress.AchievementsProgressMockDataSource;
import com.achievers.databinding.ContributionsFragBinding;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base.views.EndlessScrollView;
import com.achievers.ui.contributions.adapters.ContributionsAdapter;
import com.achievers.utils.ui.SwipeRefreshLayoutUtils;

import java.util.Random;

public class ContributionsView
        extends EndlessScrollView<AchievementProgress, ContributionsContract.Presenter, ContributionsContract.ViewModel, ContributionsFragBinding>
        implements ContributionsContract.View<ContributionsFragBinding>, BaseAdapterActionHandler<AchievementProgress>,
            SwipeRefreshLayout.OnRefreshListener {

    public ContributionsView() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.contributions_frag, container, false);

        mDataBinding = ContributionsFragBinding.bind(view);

        if (mViewModel == null) mViewModel = new ContributionsViewModel();
        if (mPresenter == null) mPresenter = new ContributionsPresenter(
            getContext(), 
            this, 
            AchievementsProgressMockDataSource.getInstance());

        mDataBinding.setViewModel(mViewModel);

        super.setUpRecycler(
                getContext(),
                new ContributionsAdapter(
                        getContext(),
                        this),
                mDataBinding.rvAchievementsProgress);

        mPresenter.refresh(mViewModel.getContainerId());

        mDataBinding.wpvContributions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initContributions();
            }
        });

        mDataBinding.wpvContributions.performClick();

        mDataBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mDataBinding.lvLevel.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mDataBinding.seekBar.setProgress(55);

        return mDataBinding.getRoot();
    }

    private void initContributions() {
        Random random = new Random();

        int[] weights = new int[7];

        for (int i = 0; i < weights.length; i++) {
            weights[i] = random.nextInt(15);
        }

        mDataBinding.wpvContributions.setWeights(weights);
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (!isActive()) return;

        SwipeRefreshLayoutUtils.setLoading(mDataBinding.refreshLayout, active);
    }

    @Override
    public RecyclerView.LayoutManager instantiateLayoutManager(Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public void onAdapterEntityClick(AchievementProgress entity) {
        // todo
    }
}
