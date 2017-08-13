package com.achievers.AchievementDetail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.AchievementDetail.Adapters.UploadEvidenceDialogSimpleAdapter;
import com.achievers.Evidence.EvidenceAdapter;
import com.achievers.R;
import com.achievers.data.models.Achievement;
import com.achievers.data.models.Evidence;
import com.achievers.data.models.UploadEvidenceItem;
import com.achievers.databinding.AchievementDetailFragBinding;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

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
        this.mViewDataBinding.setResources(this.getContext().getResources());
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
        Holder holder = new GridHolder(2);

        List<UploadEvidenceItem> uploadEvidenceItems = new ArrayList<>();

        Drawable photo = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_camera_alt_black_48dp, null);
        uploadEvidenceItems.add(new UploadEvidenceItem("Photo", photo));

        Drawable video = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_videocam_black_48dp, null);
        uploadEvidenceItems.add(new UploadEvidenceItem("Video", video));

        UploadEvidenceDialogSimpleAdapter adapter = new UploadEvidenceDialogSimpleAdapter(
                getContext(), uploadEvidenceItems, true);

        switch (item.getItemId()) {
            case R.id.menu_upload:
                final DialogPlus dialog = DialogPlus.newDialog(getContext())
                    .setContentHolder(holder)
                    .setHeader(R.layout.evidence_upload_dialog_header)
                    .setCancelable(true)
                    .setGravity(Gravity.CENTER)
                    .setAdapter(adapter)
                    .setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(DialogPlus dialog, View view) {
                            // todo
                        }
                    })
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                            Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                    item + "], position = [" + position + "]");
                        }
                    })
                    .setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogPlus dialog) {
                            // todo
                        }
                    })
                    .setOnCancelListener(new OnCancelListener() {
                        @Override
                        public void onCancel(DialogPlus dialog) {

                        }
                    })
                    .setExpanded(false)
                    .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setInAnimation(R.anim.fade_in_center)
                    .create();

                dialog.show();
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
        if (getView() == null) return;

        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}