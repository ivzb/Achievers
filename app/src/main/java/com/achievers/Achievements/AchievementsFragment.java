package com.achievers.Achievements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.AchievementDetail.AchievementDetailActivity;
import com.achievers.R;
import com.achievers.data.Achievement;
import com.achievers.data.Category;
import com.achievers.databinding.AchievementsFragBinding;
import com.achievers.util.ScrollChildSwipeRefreshLayout;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class AchievementsFragment extends Fragment implements AchievementsContract.View {

    private AchievementsContract.Presenter mPresenter;
    private AchievementsFragBinding mViewDataBinding;
    private AchievementsViewModel mAchievementsViewModel;

    public AchievementsFragment() {
        // Requires empty public constructor
    }

    public static AchievementsFragment newInstance() {
        return new AchievementsFragment();
    }

    @Override
    public void setPresenter(@NonNull AchievementsContract.Presenter presenter) {
        this.mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mPresenter.loadAchievements(this.mAchievementsViewModel.getCategory(), true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.achievements_frag, container, false);

        this.mViewDataBinding = AchievementsFragBinding.bind(view);
        this.mViewDataBinding.setViewModel(this.mAchievementsViewModel);
        this.mViewDataBinding.setActionHandler(this.mPresenter);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout = mViewDataBinding.refreshLayout;
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(mViewDataBinding.rvAchievements);

        return mViewDataBinding.getRoot();
    }

    public void setViewModel(AchievementsViewModel achievementsViewModel) {
        this.mAchievementsViewModel = achievementsViewModel;
    }

    @Override
    public void showAchievements(Category category, List<Achievement> achievements) {
        if (this.mAchievementsViewModel.getCategory() != null &&
                this.mAchievementsViewModel.getCategory().equals(category) &&
                this.mAchievementsViewModel.getAdapter() != null &&
                this.mAchievementsViewModel.getAdapter().getItemCount() > 0) { // endless scroll is loading more items
            this.mAchievementsViewModel.getAdapter().addAchievements(achievements);
        } else { // new category has been loaded
            AchievementsAdapter adapter = new AchievementsAdapter(achievements, category, this.mPresenter);
            this.mAchievementsViewModel.setAdapter(adapter);
            this.mAchievementsViewModel.setCategory(category);
        }
    }


    @Override
    public void showAchievementDetailsUi(int achievementId) {
        // in it's own Activity, since it makes more sense that way and it gives us the flexibility
        // to show some Intent stubbing.
        Intent intent = new Intent(getContext(), AchievementDetailActivity.class);
        intent.putExtra(AchievementDetailActivity.EXTRA_ACHIEVEMENT_ID, achievementId);
        startActivity(intent);
    }


    @Override
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) return;

        final SwipeRefreshLayout srl = (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showLoadingError() {
        showMessage(getString(R.string.loading_achievements_error));
    }


    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public boolean isActive() {
        return isAdded();
    }
}
