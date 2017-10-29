package com.achievers.ui.achieveemnts_progress;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.AchievementProgress;
import com.achievers.data.source.achievements_progress.AchievementsProgressMockDataSource;
import com.achievers.databinding.ProfileFragBinding;
import com.achievers.ui._base.AbstractFragment;
import com.achievers.ui._base.contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base.contracts.adapters.BaseAdapter;
import com.achievers.ui.achieveemnts_progress.adapter.AchievementsProgressAdapter;
import com.achievers.utils.ui.EndlessRecyclerViewScrollListener;

import java.util.List;

public class AchievementsProgressFragment
        extends AbstractFragment<AchievementsProgressContract.Presenter, AchievementsProgressContract.ViewModel, ProfileFragBinding>
        implements AchievementsProgressContract.View<ProfileFragBinding>, BaseAdapterActionHandler<AchievementProgress>,
            SwipeRefreshLayout.OnRefreshListener {

    public AchievementsProgressFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_frag, container, false);

        mDataBinding = ProfileFragBinding.bind(view);

        if (mViewModel == null) mViewModel = new AchievementsProgressViewModel();
        if (mPresenter == null) mPresenter = new AchievementsProgressPresenter(
            getContext(), 
            this, 
            AchievementsProgressMockDataSource.getInstance());

        mDataBinding.setViewModel(mViewModel);

        setUpAchievementsProgressRecycler(getContext());
        setUpLoadingIndicator();

        mPresenter.refresh();

        return mDataBinding.getRoot();
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (!isActive()) return;

        final SwipeRefreshLayout srl = mDataBinding.refreshLayout;

        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);

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

    private void setUpLoadingIndicator() {
        mDataBinding.refreshLayout.setColorSchemeColors(
            getColor(R.color.primary),
            getColor(R.color.accent),
            getColor(R.color.dark)
        );

        mDataBinding.refreshLayout.setScrollUpChild(mDataBinding.rvAchievementsProgress);
        mDataBinding.refreshLayout.setOnRefreshListener(this);
    }

    private int getColor(int color) {
        return ContextCompat.getColor(getActivity(), color);
    }

    @Override
    public void onAdapterEntityClick(AchievementProgress entity) {
        // todo
    }
}
