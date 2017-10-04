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
import com.achievers.ui.achievement.AchievementActivity;
import com.achievers.ui.add_achievement.AddAchievementActivity;
import com.achievers.ui._base.AbstractFragment;

import org.parceler.Parcels;

import java.util.List;

public class Fragment
        extends AbstractFragment<Contracts.Presenter, Contracts.ViewModel>
        implements Contracts.View, View.OnClickListener, ActionHandler {

    private AchievementsFragBinding mViewDataBinding;

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

        FloatingActionButton fab = getActivity().findViewById(R.id.fabAddAchievement);
        fab.setOnClickListener(this);

        setUpLoadingIndicator();

        return mViewDataBinding.getRoot();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        mPresenter.result(requestCode, resultCode);
//    }

    @Override
    public void showAchievements(List<Achievement> achievements) {
        mViewModel.getAdapter().addAchievements(achievements);
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
        mPresenter.clickAddAchievement();
    }

    @Override
    public void initRecycler(
            Contracts.Adapter adapter,
            LinearLayoutManager layoutManager,
            RecyclerView.OnScrollListener scrollListener) {

        mViewModel.setAdapter(adapter);

        mViewDataBinding.rvAchievements.setAdapter((RecyclerView.Adapter) adapter);
        mViewDataBinding.rvAchievements.setLayoutManager(layoutManager);
        mViewDataBinding.rvAchievements.addOnScrollListener(scrollListener);
    }

    @Override
    public void onAchievementClick(Achievement achievement) {
        mPresenter.clickAchievement(achievement);
    }

    private void setUpLoadingIndicator() {
        mViewDataBinding.refreshLayout.setColorSchemeColors(
            getColor(R.color.colorPrimary),
            getColor(R.color.colorAccent),
            getColor(R.color.colorPrimaryDark)
        );

        mViewDataBinding.refreshLayout.setScrollUpChild(mViewDataBinding.rvAchievements);
    }

    private int getColor(int color) {
        return ContextCompat.getColor(getActivity(), color);
    }
}