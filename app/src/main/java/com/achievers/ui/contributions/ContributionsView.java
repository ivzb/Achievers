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
import com.achievers.data.source.achievements_progress.AchievementsProgressMockDataSource;
import com.achievers.databinding.ContributionsFragBinding;
import com.achievers.ui._base.AbstractView;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base._contracts.adapters.BaseAdapter;
import com.achievers.ui.contributions.adapters.ContributionsAdapter;
import com.achievers.utils.ui.EndlessRecyclerViewScrollListener;

import java.util.List;
import java.util.Random;

public class ContributionsView
        extends AbstractView<ContributionsContract.Presenter, ContributionsContract.ViewModel, ContributionsFragBinding>
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

        setUpAchievementsProgressRecycler(getContext());

        mPresenter.refresh();

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
    public void setLoadingIndicator(boolean active) {
        // todo
    }

    @Override
    public void showAchievementsProgress(
        List<AchievementProgress> achievementsProgress) {

        mViewModel.getAdapter().add(achievementsProgress);
    }

    @Override
    public void openAchievementProgressUi(
        AchievementProgress achievementProgress) {

        // todo
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
    public void onRefresh() {
        mPresenter.refresh();
    }

    private void setUpAchievementsProgressRecycler(Context context) {
        BaseAdapter<AchievementProgress> adapter = new ContributionsAdapter(
            getContext(), 
            this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        mViewModel.setAdapter(adapter);

        mDataBinding.rvAchievementsProgress.setAdapter((RecyclerView.Adapter) adapter);
        mDataBinding.rvAchievementsProgress.setLayoutManager(layoutManager);
        mDataBinding.rvAchievementsProgress.addOnScrollListener(
            new EndlessRecyclerViewScrollListener(layoutManager) {

                @Override
                public void onLoadMore(
                    int page, 
                    int totalItemsCount, 
                    RecyclerView view) {

                    mPresenter.loadAchievementsProgress(page);
                }
        });
    }

    @Override
    public void onAdapterEntityClick(AchievementProgress entity) {
        // todo
    }
}
