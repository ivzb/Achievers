package com.achievers.ui.achievement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Evidence;
import com.achievers.databinding.AchievementFragBinding;
import com.achievers.ui._base.views.EndlessScrollView;
import com.achievers.ui.achievement.adapters.EvidencesAdapter;
import com.achievers.ui.achievement.adapters.UploadEvidenceAdapter;
import com.achievers.ui.add_evidence.AddEvidenceActivity;
import com.achievers.ui.evidence.EvidenceActivity;
import com.achievers.utils.ui.SwipeRefreshLayoutUtils;
import com.achievers.utils.ui.multimedia.MultimediaType;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;

public class AchievementView
        extends EndlessScrollView<Evidence, AchievementContract.Presenter, AchievementContract.ViewModel, AchievementFragBinding>
        implements AchievementContract.View<AchievementFragBinding>,
                   SwipeRefreshLayout.OnRefreshListener {

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

        super.setUpRecycler(
                getContext(),
                new EvidencesAdapter(getContext(), this),
                mDataBinding.rvEvidences);

        SwipeRefreshLayoutUtils.setup(
                getContext(),
                mDataBinding.refreshLayout,
                mDataBinding.rvEvidences,
                this);

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
            mPresenter.refresh(mViewModel.getContainerId());
        }

        return view;
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
        MultimediaType[] types = MultimediaType.values();
        UploadEvidenceAdapter adapter = new UploadEvidenceAdapter(getContext(), types);

        switch (item.getItemId()) {
            case R.id.menu_upload:
                final DialogPlus dialog = DialogPlus.newDialog(getContext())
                    .setContentHolder(new GridHolder(3))
                    .setHeader(R.layout.evidence_upload_dialog_header)
                    .setCancelable(true)
                    .setGravity(Gravity.CENTER)
                    .setAdapter(adapter)
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                            Intent intent = new Intent(getContext(), AddEvidenceActivity.class);

                            intent.putExtra(AddEvidenceActivity.AchievementIdExtra, mViewModel.getContainerId());
                            intent.putExtra(AddEvidenceActivity.MultimediaTypeExtra, (MultimediaType) item);

                            startActivityForResult(intent, AddEvidenceActivity.REQUEST_ADD_EVIDENCE);

                            dialog.dismiss();
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

        SwipeRefreshLayoutUtils.setLoading(mDataBinding.refreshLayout, active);
    }

    @Override
    public void onAdapterEntityClick(Evidence entity) {
        Intent intent = new Intent(getContext(), EvidenceActivity.class);
        intent.putExtra(EvidenceActivity.EXTRA_EVIDENCE_ID, entity.getId());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh(mViewModel.getContainerId());
    }
}