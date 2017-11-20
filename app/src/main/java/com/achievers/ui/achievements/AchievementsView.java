package com.achievers.ui.achievements;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsMockDataSource;
import com.achievers.databinding.AchievementsFragBinding;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base._contracts.adapters.BaseAdapter;
import com.achievers.ui._base.views.EndlessAdapterView;
import com.achievers.ui.achievement.AchievementActivity;
import com.achievers.ui.achievements.adapters.AchievementsAdapter;
import com.achievers.utils.ui.EndlessRecyclerViewScrollListener;

import org.parceler.Parcels;

public class AchievementsView
        extends EndlessAdapterView<Achievement, AchievementsContract.Presenter, AchievementsContract.ViewModel, AchievementsFragBinding>
        implements AchievementsContract.View<AchievementsFragBinding>,
                   BaseAdapterActionHandler<Achievement>,
                   SwipeRefreshLayout.OnRefreshListener {

    private static final String ACHIEVEMENTS_STATE = "achievements_state";
    private static final String LAYOUT_MANAGER_STATE = "layout_manager_state";
    private static final String PAGE_STATE = "page_state";

    public AchievementsView() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.achievements_frag, container, false);

        mDataBinding = AchievementsFragBinding.bind(view);

        if (mViewModel == null) {
            mViewModel = new AchievementsViewModel();
        }

        if (mPresenter == null) {
            mPresenter = new AchievementsPresenter(
                    getContext(),
                    this,
                    AchievementsMockDataSource.getInstance());
        }

        mDataBinding.setViewModel(mViewModel);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(PAGE_STATE)) {
                int page = savedInstanceState.getInt(PAGE_STATE);
                setPage(page);
            }
        }

        setUpAchievementsRecycler(getContext());
        setUpLoadingIndicator();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ACHIEVEMENTS_STATE)) {
                Parcelable achievementsState = savedInstanceState.getParcelable(ACHIEVEMENTS_STATE);
                mViewModel.getAdapter().onRestoreInstanceState(achievementsState);
            }

            if (savedInstanceState.containsKey(LAYOUT_MANAGER_STATE)) {
                Parcelable layoutManagerState = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE);
                mDataBinding.rvAchievements.getLayoutManager().onRestoreInstanceState(layoutManagerState);
            }
        } else {
            mPresenter.refresh();
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
                outState.putParcelable(ACHIEVEMENTS_STATE, achievementsState);
            }
        }

        if (mDataBinding.rvAchievements != null && mDataBinding.rvAchievements.getLayoutManager() != null) {
            Parcelable layoutManagerState = mDataBinding.rvAchievements.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(LAYOUT_MANAGER_STATE, layoutManagerState);
        }
    }

    @Override
    public void openUi(Achievement achievement) {
        Intent intent = new Intent(getContext(), AchievementActivity.class);
        intent.putExtra(AchievementActivity.EXTRA_ACHIEVEMENT, Parcels.wrap(achievement));
        startActivity(intent);
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
    public void onAdapterEntityClick(Achievement achievement) {
        mPresenter.click(achievement);
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    private void setUpAchievementsRecycler(Context context) {
        BaseAdapter<Achievement> adapter = new AchievementsAdapter(getContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        mViewModel.setAdapter(adapter);

        mDataBinding.rvAchievements.setAdapter((RecyclerView.Adapter) adapter);
        mDataBinding.rvAchievements.setLayoutManager(layoutManager);
        mDataBinding.rvAchievements.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager, mViewModel.getPage()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.load(page);
            }
        });
    }

    private void setUpLoadingIndicator() {
        mDataBinding.refreshLayout.setColorSchemeColors(
            getColor(R.color.primary),
            getColor(R.color.accent),
            getColor(R.color.dark)
        );

        mDataBinding.refreshLayout.setScrollUpChild(mDataBinding.rvAchievements);
        mDataBinding.refreshLayout.setOnRefreshListener(this);
    }

    private int getColor(int color) {
        return ContextCompat.getColor(getActivity(), color);
    }
}