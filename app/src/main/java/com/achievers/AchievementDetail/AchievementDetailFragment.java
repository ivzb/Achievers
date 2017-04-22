package com.achievers.AchievementDetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.achievers.Evidence.EvidenceAdapter;
import com.achievers.Evidence.EvidenceViewModel;
import com.achievers.R;
import com.achievers.data.Achievement;
import com.achievers.data.Evidence;
import com.achievers.databinding.AchievementDetailFragBinding;

/**
 * Main UI for the task detail screen.
 */
public class AchievementDetailFragment extends Fragment implements AchievementDetailContract.View {

    public static final String ARGUMENT_ACHIEVEMENT_ID = "ACHIEVEMENT_ID";

    public static final int REQUEST_EDIT_ACHIEVEMENT = 1;

    private AchievementDetailContract.Presenter mPresenter;
    private AchievementDetailFragBinding mViewDataBinding;
    private AchievementDetailViewModel mViewModel;

    public static AchievementDetailFragment newInstance(int achievementId) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_ACHIEVEMENT_ID, achievementId);
        AchievementDetailFragment fragment = new AchievementDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void setPresenter(AchievementDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.achievement_detail_frag, container, false);

        this.mViewDataBinding = AchievementDetailFragBinding.bind(view);
        this.mViewDataBinding.setActionHandler(this.mPresenter);
        this.mViewDataBinding.setViewModel(this.mViewModel);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        // Set up floating action button
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_achievement);
        fab.setVisibility(View.GONE); // gone until functionality implemented

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int achievementId = getArguments().getInt(ARGUMENT_ACHIEVEMENT_ID, 0);
//                Intent intent = new Intent(getContext(), AddEditAchievementActivity.class);
//                intent.putExtra(AddEditAchievementFragment.ARGUMENT_EDIT_ACHIEVEMENT_ID, achievementId);
//                startActivityForResult(intent, REQUEST_EDIT_ACHIEVEMENT);
            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_something:
//                int achievementId = getArguments().getInt(ARGUMENT_ACHIEVEMENT_ID, 0);
//                mPresenter.something();
                return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.achievement_detail_fragment_menu, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_EDIT_ACHIEVEMENT:
                if (resultCode == Activity.RESULT_OK) {
                    getActivity().finish();
                    return;
                }

                break;
        }
    }

    public void setViewModel(AchievementDetailViewModel viewModel) {
        this.mViewModel = viewModel;
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
    public void showAchievement(Achievement achievement) {
        this.mViewModel.setAchievement(achievement);
    }

    @Override
    public void showEvidence(List<Evidence> evidence) {
        EvidenceAdapter adapter = new EvidenceAdapter(evidence, mPresenter);
        this.mViewModel.setEvidenceAdapter(adapter);
    }

    @Override
    public void showLoadingAchievementError() {
        showMessage(getString(R.string.loading_achievement_error));
    }

    @Override
    public void showLoadingEvidenceError() {
        showMessage(getString(R.string.loading_evidence_error));
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}