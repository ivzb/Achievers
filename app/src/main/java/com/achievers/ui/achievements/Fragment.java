package com.achievers.ui.achievements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.ui.achievement.AchievementDetailActivity;
import com.achievers.ui.addachievement.AddAchievementActivity;
import com.achievers.R;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Category;
import com.achievers.databinding.AchievementsFragBinding;
import com.achievers.ui.base.BaseFragment;
import com.achievers.utils.ScrollChildSwipeRefreshLayout;

import java.util.List;

public class Fragment extends BaseFragment<Contracts.Presenter>
        implements Contracts.View, View.OnClickListener {

    private AchievementsFragBinding mViewDataBinding;
    private ViewModel mAchievementsViewModel;

    public Fragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.achievements_frag, container, false);

        mViewDataBinding = AchievementsFragBinding.bind(view);
        mViewDataBinding.setViewModel(mAchievementsViewModel);
        mViewDataBinding.setActionHandler(mPresenter);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_achievement);
        fab.setOnClickListener(this);

        setUpLoadingIndicator();

        return mViewDataBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void showAchievements(/*Category category, */List<Achievement> achievements) {
//        if (mAchievementsViewModel.getCategory() != null &&
//                mAchievementsViewModel.getCategory().equals(category) &&
//                mAchievementsViewModel.getAdapter() != null &&
//                mAchievementsViewModel.getAdapter().getItemCount() > 0) {
//
//            // endless scroll is loading more items
//            mAchievementsViewModel.getAdapter().addAchievements(achievements);
//            return;
//        }
//
//        // new category has been loaded
//        Adapter adapter = new Adapter(achievements, category, mPresenter);
//        mAchievementsViewModel.setAdapter(adapter);
//        mAchievementsViewModel.setCategory(category);

        // todo: add data to adapter
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

    public void setViewModel(ViewModel achievementsViewModel) {
        this.mAchievementsViewModel = achievementsViewModel;
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
}