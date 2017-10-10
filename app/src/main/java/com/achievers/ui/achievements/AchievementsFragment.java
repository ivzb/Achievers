package com.achievers.ui.achievements;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.Config;
import com.achievers.R;
import com.achievers.data.entities.Achievement;
import com.achievers.databinding.AchievementsFragBinding;
import com.achievers.ui._base.AbstractFragment;
import com.achievers.ui.achievement.AchievementActivity;
import com.achievers.ui.add_achievement.AddAchievementActivity;
import com.achievers.utils.EndlessRecyclerViewScrollListener;

import org.parceler.Parcels;

import java.util.List;

public class AchievementsFragment
        extends AbstractFragment<AchievementsContracts.Presenter, AchievementsContracts.ViewModel, AchievementsFragBinding>
        implements AchievementsContracts.View<AchievementsFragBinding>, View.OnClickListener,
            AchievementsActionHandler, SwipeRefreshLayout.OnRefreshListener {

    private static final String ACHIEVEMENTS_STATE = "achievements_state";
    private static final String LAYOUT_MANAGER_STATE = "layout_manager_state";
    private static final String PAGE_STATE = "page_state";

    public AchievementsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.achievements_frag, container, false);

        mDataBinding = AchievementsFragBinding.bind(view);
        mDataBinding.setViewModel(mViewModel);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(PAGE_STATE)) {
                int page = savedInstanceState.getInt(PAGE_STATE);
                setPage(page);
            }
        }

        setUpAchievementsRecycler(getContext());
        setUpLoadingIndicator();
        setUpFab();

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
            mPresenter.loadAchievements(Config.sRecyclerInitialPage);
        }

        return mDataBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Parcelable achievementsState = mViewModel.getAdapter().onSaveInstanceState();
        outState.putParcelable(ACHIEVEMENTS_STATE, achievementsState);

        Parcelable layoutManagerState = mDataBinding.rvAchievements.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(LAYOUT_MANAGER_STATE, layoutManagerState);

        outState.putInt(PAGE_STATE, mViewModel.getPage());
    }

        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void showAchievements(List<Achievement> achievements) {
        mViewModel.getAdapter().add(achievements);
    }

    @Override
    public void openAchievementUi(Achievement achievement) {
        Intent intent = new Intent(getContext(), AchievementActivity.class);
        intent.putExtra(AchievementActivity.EXTRA_ACHIEVEMENT_ID, Parcels.wrap(achievement));
        startActivity(intent);
    }

    @Override
    public void openAddAchievementUi() {
        Intent intent = new Intent(getContext(), AddAchievementActivity.class);
        startActivityForResult(intent, AddAchievementActivity.REQUEST_ADD_ACHIEVEMENT);
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
    public void onClick(View view) {
        mPresenter.clickAddAchievement();
    }

    @Override
    public void onAchievementClick(Achievement achievement) {
        mPresenter.clickAchievement(achievement);
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    private void setUpAchievementsRecycler(Context context) {
        AchievementsContracts.Adapter adapter = new AchievementsAdapter(getContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        mViewModel.setAdapter(adapter);

        mDataBinding.rvAchievements.setAdapter((RecyclerView.Adapter) adapter);
        mDataBinding.rvAchievements.setLayoutManager(layoutManager);
        mDataBinding.rvAchievements.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager, mViewModel.getPage()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.loadAchievements(page);
            }
        });
    }

    private void setUpLoadingIndicator() {
        mDataBinding.refreshLayout.setColorSchemeColors(
            getColor(R.color.colorPrimary),
            getColor(R.color.colorAccent),
            getColor(R.color.colorPrimaryDark)
        );

        mDataBinding.refreshLayout.setScrollUpChild(mDataBinding.rvAchievements);
        mDataBinding.refreshLayout.setOnRefreshListener(this);
    }

    private void setUpFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fabAddAchievement);
        fab.setOnClickListener(this);
    }

    private int getColor(int color) {
        return ContextCompat.getColor(getActivity(), color);
    }
}