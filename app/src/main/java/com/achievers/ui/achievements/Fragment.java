package com.achievers.ui.achievements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Achievement;
import com.achievers.databinding.AchievementsFragBinding;
import com.achievers.ui.achievement.AchievementDetailActivity;
import com.achievers.ui.add_achievement.AddAchievementActivity;
import com.achievers.ui.base.BaseFragment;
import com.achievers.utils.EndlessRecyclerViewScrollListener;
import com.achievers.utils.ScrollChildSwipeRefreshLayout;

import java.util.List;

public class Fragment
        extends BaseFragment<Contracts.Presenter, Contracts.ViewModel>
        implements Contracts.View, View.OnClickListener {

    private AchievementsFragBinding mViewDataBinding;
    private Contracts.Adapter mAdapter;

    public Fragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.achievements_frag, container, false);

        mViewDataBinding = AchievementsFragBinding.bind(view);
        mViewDataBinding.setViewModel((ViewModel) mViewModel);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_achievement);
        fab.setOnClickListener(this);

        setUpLoadingIndicator();

        return mViewDataBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        setUpRecycler();

        int initialPage = 0;
        mPresenter.loadAchievements(initialPage);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        mPresenter.result(requestCode, resultCode);
//    }

    @Override
    public void showAchievements(List<Achievement> achievements) {
        mAdapter.addAchievements(achievements);
    }

    @Override
    public void showAchievementDetailsUi(/*int achievementId*/) {
        Intent intent = new Intent(getContext(), AchievementDetailActivity.class);
//        intent.putExtra(AchievementDetailActivity.EXTRA_ACHIEVEMENT_ID, achievementId);
        startActivity(intent);
    }

    @Override
    public void showAddAchievementUi(final int categoryId) {
        Intent intent = new Intent(getContext(), AddAchievementActivity.class);
        intent.putExtra(AddAchievementActivity.EXTRA_CATEGORY_ID, categoryId);
        startActivity(intent);
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (!isActive()) return;

        final SwipeRefreshLayout srl = mViewDataBinding.refreshLayout;

        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void onClick(View view) {
        showAddAchievementUi(-1);
    }

    private void setUpLoadingIndicator() {
        final ScrollChildSwipeRefreshLayout srl = mViewDataBinding.refreshLayout;

        srl.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        srl.setScrollUpChild(mViewDataBinding.rvAchievements);
    }

    private void setUpRecycler() {
        mAdapter = new Adapter(mPresenter);
        mViewDataBinding.rvAchievements.setAdapter((RecyclerView.Adapter) mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mViewDataBinding.rvAchievements.setLayoutManager(layoutManager);

        mViewDataBinding.rvAchievements.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.loadAchievements(page);
            }
        });
    }
}