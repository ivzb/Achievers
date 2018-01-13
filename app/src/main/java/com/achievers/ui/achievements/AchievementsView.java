package com.achievers.ui.achievements;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Achievement;
import com.achievers.data.sources.DataSources;
import com.achievers.databinding.AchievementsFragBinding;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base.views.EndlessScrollView;
import com.achievers.ui.achievement.AchievementActivity;
import com.achievers.ui.achievements.adapters.AchievementsAdapter;
import com.achievers.utils.ui.SwipeRefreshLayoutUtils;

public class AchievementsView
        extends EndlessScrollView<Achievement, AchievementsContract.Presenter, AchievementsContract.ViewModel, AchievementsFragBinding>
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
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.achievements_frag, container, false);

        mDataBinding = AchievementsFragBinding.bind(view);

        if (mViewModel == null) {
            mViewModel = new AchievementsViewModel();
        }

        if (mPresenter == null) {
            mPresenter = new AchievementsPresenter(
                    getContext(),
                    this,
                    DataSources.getInstance().getAchievements());
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
                new AchievementsAdapter(getContext(), this),
                mDataBinding.rvAchievements);

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
        intent.putExtra(AchievementActivity.EXTRA_ACHIEVEMENT_ID, achievement.getId());
        startActivity(intent);
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (!isActive()) return;

        SwipeRefreshLayoutUtils.setLoading(mDataBinding.refreshLayout, active);
    }

    @Override
    public void onAdapterEntityClick(Achievement achievement) {
        mPresenter.click(achievement);
    }
}