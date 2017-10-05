package com.achievers.ui.achievements;

import android.content.Context;
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
import com.achievers.ui._base.AbstractFragment;
import com.achievers.ui.achievement.AchievementActivity;
import com.achievers.ui.add_achievement.AddAchievementActivity;
import com.achievers.utils.EndlessRecyclerViewScrollListener;

import org.parceler.Parcels;

import java.util.List;

public class AchievementsFragment
        extends AbstractFragment<AchievementsContracts.Presenter, AchievementsContracts.ViewModel, AchievementsFragBinding>
        implements AchievementsContracts.View<AchievementsFragBinding>, View.OnClickListener, AchievementsActionHandler {

    public AchievementsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.achievements_frag, container, false);

        mDataBinding = AchievementsFragBinding.bind(view);

        mDataBinding.setViewModel((AchievementsViewModel) mViewModel);

        setUpAchievementsRecycler(getContext());
        setUpLoadingIndicator();
        setUpFab();

        return mDataBinding.getRoot();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        mPresenter.resultPermission(requestCode, resultCode);
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
        startActivityForResult(intent, AddAchievementActivity.REQUEST_ADD_ACHIEVEMENT);
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

    private void setUpAchievementsRecycler(Context context) {
        AchievementsContracts.Adapter adapter = new AchievementsAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        mViewModel.setAdapter(adapter);

        mDataBinding.rvAchievements.setAdapter((RecyclerView.Adapter) adapter);
        mDataBinding.rvAchievements.setLayoutManager(layoutManager);
        mDataBinding.rvAchievements.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
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
    }

    private void setUpFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fabAddAchievement);
        fab.setOnClickListener(this);
    }

    private int getColor(int color) {
        return ContextCompat.getColor(getActivity(), color);
    }
}