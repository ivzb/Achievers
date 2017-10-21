package com.achievers.ui.achievement;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Evidence;
import com.achievers.data.entities.UploadEvidenceItem;
import com.achievers.databinding.AchievementFragBinding;
import com.achievers.ui._base.AbstractFragment;
import com.achievers.ui._base.contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui.achievement.adapters.EvidencesAdapter;
import com.achievers.ui.achievement.adapters.UploadEvidenceDialogSimpleAdapter;
import com.achievers.ui.evidence.EvidenceActivity;
import com.achievers.utils.ui.EndlessRecyclerViewScrollListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class AchievementFragment
        extends AbstractFragment<AchievementContract.Presenter, AchievementContract.ViewModel, AchievementFragBinding>
        implements AchievementContract.View<AchievementFragBinding>, View.OnClickListener,
        BaseAdapterActionHandler<Evidence>, SwipeRefreshLayout.OnRefreshListener {

    private static final String EVIDENCES_STATE = "evidences_state";
    private static final String LAYOUT_MANAGER_STATE = "layout_manager_state";
    private static final String PAGE_STATE = "page_state";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.achievement_frag, container, false);

        mDataBinding = AchievementFragBinding.bind(view);
        mDataBinding.setViewModel(mViewModel);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(PAGE_STATE)) {
                int page = savedInstanceState.getInt(PAGE_STATE);
                setPage(page);
            }
        }

        setUpEvidencesRecycler(getContext());
        setUpLoadingIndicator();
        setUpFab();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(EVIDENCES_STATE)) {
                Parcelable evidencesState = savedInstanceState.getParcelable(EVIDENCES_STATE);
                mViewModel.getAdapter().onRestoreInstanceState(evidencesState);
            }

            if (savedInstanceState.containsKey(LAYOUT_MANAGER_STATE)) {
                Parcelable layoutManagerState = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE);
                mDataBinding.rvEvidences.getLayoutManager().onRestoreInstanceState(layoutManagerState);
            }
        } else {
            long achievementId = mViewModel.getAchievement().getId();
            mPresenter.refresh(achievementId);
        }

        return view;

        // todo: achievement module, showing evidence previews,
        // todo: onClick opens specific evidence fragment with player and navigation (prev and next)
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mViewModel != null) {
            outState.putInt(PAGE_STATE, mViewModel.getPage());

            if (mViewModel.getAdapter() != null) {
                Parcelable evidencesState = mViewModel.getAdapter().onSaveInstanceState();
                outState.putParcelable(EVIDENCES_STATE, evidencesState);
            }
        }

        if (mDataBinding.rvEvidences != null && mDataBinding.rvEvidences.getLayoutManager() != null) {
            Parcelable layoutManagerState = mDataBinding.rvEvidences.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(LAYOUT_MANAGER_STATE, layoutManagerState);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewModel.getAdapter().releaseMedia();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.upload_fragment_menu, menu);
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
    public void showEvidences(List<Evidence> evidence) {
        mViewModel.getAdapter().add(evidence);
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
    public void onClick(View v) {
        // todo
    }

    @Override
    public void onAdapterEntityClick(Evidence entity) {
        Intent intent = new Intent(getContext(), EvidenceActivity.class);
        intent.putExtra(EvidenceActivity.EXTRA_EVIDENCE, Parcels.wrap(entity));
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh(mViewModel.getAchievement().getId());
    }

    private void setUpEvidencesRecycler(Context context) {
        final EvidencesAdapter adapter = new EvidencesAdapter(getContext(),this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mViewModel.setAdapter(adapter);

        mDataBinding.rvEvidences.setAdapter(adapter);
        mDataBinding.rvEvidences.setLayoutManager(layoutManager);
        mDataBinding.rvEvidences.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager, mViewModel.getPage()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.loadEvidences(mViewModel.getAchievement().getId(), page);
            }
        });
    }

    private void setUpFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fabAddEvidence);
        fab.setOnClickListener(this);
    }

    private void setUpLoadingIndicator() {
        mDataBinding.refreshLayout.setColorSchemeColors(
                getColor(R.color.colorPrimary),
                getColor(R.color.colorAccent),
                getColor(R.color.colorPrimaryDark)
        );

        mDataBinding.refreshLayout.setScrollUpChild(mDataBinding.rvEvidences);
        mDataBinding.refreshLayout.setOnRefreshListener(this);
    }

    private int getColor(int color) {
        return ContextCompat.getColor(getActivity(), color);
    }
}