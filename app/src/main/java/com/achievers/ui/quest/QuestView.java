package com.achievers.ui.quest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.databinding.QuestFragBinding;
import com.achievers.ui._base._contracts.action_handlers.BaseActionHandler;
import com.achievers.ui._base.views.EndlessAdapterView;
import com.achievers.ui.achievement.AchievementActivity;
import com.achievers.ui.achievements.adapters.AchievementsAdapter;
import com.achievers.ui.rewards.RewardsActivity;
import com.achievers.utils.ui.EndlessRecyclerViewScrollListener;
import com.achievers.utils.ui.SwipeRefreshLayoutUtils;

import org.parceler.Parcels;

import java.util.List;

public class QuestView
        extends EndlessAdapterView<Achievement, QuestContract.Presenter, QuestContract.ViewModel, QuestFragBinding>
        implements QuestContract.View<QuestFragBinding>,
                   SwipeRefreshLayout.OnRefreshListener,
                   View.OnClickListener,
                   BaseActionHandler {

    private static final String ACHIEVEMENTS_STATE = "achievements_state";
    private static final String LAYOUT_MANAGER_STATE = "layout_manager_state";
    private static final String PAGE_STATE = "page_state";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.quest_frag, container, false);

        mDataBinding = QuestFragBinding.bind(view);
        mDataBinding.setViewModel(mViewModel);
        mDataBinding.setActionHandler(this);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(PAGE_STATE)) {
                int page = savedInstanceState.getInt(PAGE_STATE);
                setPage(page);
            }
        }

        setUpAchievementsRecycler(getContext());
        setUpFab();

        SwipeRefreshLayoutUtils.setup(
                getContext(),
                mDataBinding.refreshLayout,
                mDataBinding.rvAchievements,
                this);

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
            long questId = mViewModel.getQuest().getId();
            mPresenter.refresh(questId);
        }

        List<Reward> rewards = mViewModel.getQuest().getRewards();
        Uri[] rewardsUris = new Uri[rewards.size()];

        for (int i = 0; i < rewards.size(); i++) {
            rewardsUris[i] = rewards.get(i).getPictureUri();
        }

        mDataBinding.mdvRewards.setUris(rewardsUris);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mViewModel != null) {
            outState.putInt(PAGE_STATE, mViewModel.getPage());

            if (mViewModel.getAdapter() != null) {
                Parcelable evidencesState = mViewModel.getAdapter().onSaveInstanceState();
                outState.putParcelable(ACHIEVEMENTS_STATE, evidencesState);
            }
        }

        if (mDataBinding.rvAchievements != null && mDataBinding.rvAchievements.getLayoutManager() != null) {
            Parcelable layoutManagerState = mDataBinding.rvAchievements.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(LAYOUT_MANAGER_STATE, layoutManagerState);
        }
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (!isActive()) return;

        SwipeRefreshLayoutUtils.setLoading(mDataBinding.refreshLayout, active);
    }

    @Override
    public void onAdapterEntityClick(Achievement achievement) {
        Intent intent = new Intent(getContext(), AchievementActivity.class);
        intent.putExtra(AchievementActivity.EXTRA_ACHIEVEMENT, Parcels.wrap(achievement));
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh(mViewModel.getQuest().getId());
    }

    private void setUpAchievementsRecycler(Context context) {
        final AchievementsAdapter adapter = new AchievementsAdapter(getContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mViewModel.setAdapter(adapter);

        mDataBinding.rvAchievements.setAdapter(adapter);
        mDataBinding.rvAchievements.setLayoutManager(layoutManager);
        mDataBinding.rvAchievements.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager, mViewModel.getPage()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.load(mViewModel.getQuest().getId(), page);
            }
        });
    }

    private void setUpFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fabQuest);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick() {
        mPresenter.clickRewards(mViewModel.getQuest());
    }

    @Override
    public void openRewardsUi(Quest quest) {
        Intent intent = new Intent(getContext(), RewardsActivity.class);
        intent.putExtra(RewardsActivity.EXTRA_QUEST, Parcels.wrap(quest));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        // todo
    }
}
