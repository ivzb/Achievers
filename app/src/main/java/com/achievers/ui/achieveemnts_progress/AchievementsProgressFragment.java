package com.achievers.ui.achieveemnts_progress;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.AchievementProgress;
import com.achievers.data.source.achievements_progress.AchievementsProgressMockDataSource;
import com.achievers.databinding.AchievementProgressFragBinding;
import com.achievers.ui._base.AbstractFragment;
import com.achievers.ui._base.contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base.contracts.adapters.BaseAdapter;
import com.achievers.ui.achieveemnts_progress.adapter.AchievementsProgressAdapter;
import com.achievers.utils.ui.EndlessRecyclerViewScrollListener;

import java.util.List;
import java.util.Random;

public class AchievementsProgressFragment
        extends AbstractFragment<AchievementsProgressContract.Presenter, AchievementsProgressContract.ViewModel, AchievementProgressFragBinding>
        implements AchievementsProgressContract.View<AchievementProgressFragBinding>, BaseAdapterActionHandler<AchievementProgress>,
            SwipeRefreshLayout.OnRefreshListener {

    public AchievementsProgressFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.achievement_progress_frag, container, false);

        mDataBinding = AchievementProgressFragBinding.bind(view);

        if (mViewModel == null) mViewModel = new AchievementsProgressViewModel();
        if (mPresenter == null) mPresenter = new AchievementsProgressPresenter(
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
        BaseAdapter<AchievementProgress> adapter = new AchievementsProgressAdapter(
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
