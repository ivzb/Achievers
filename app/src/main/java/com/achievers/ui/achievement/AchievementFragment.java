package com.achievers.ui.achievement;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Evidence;
import com.achievers.data.entities.UploadEvidenceItem;
import com.achievers.databinding.AchievementFragBinding;
import com.achievers.ui._base.AbstractFragment;
import com.achievers.ui.achievement.adapters.UploadEvidenceDialogSimpleAdapter;
import com.achievers.ui.evidence.EvidenceAdapter;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;

import java.util.ArrayList;
import java.util.List;

public class AchievementFragment
        extends AbstractFragment<AchievementContract.Presenter, AchievementContract.ViewModel, AchievementFragBinding>
        implements AchievementContract.View<AchievementFragBinding> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.achievement_frag, container, false);

        mDataBinding = AchievementFragBinding.bind(view);
        mDataBinding.setActionHandler(mPresenter);
        mDataBinding.setResources(getContext().getResources());
        mDataBinding.setViewModel(mViewModel);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.achievement_fragment_menu, menu);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case REQUEST_EDIT_ACHIEVEMENT:
//                if (resultCode == Activity.RESULT_OK) {
//                    getActivity().finish();
//                    return;
//                }
//
//                break;
//        }
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        final SwipeRefreshLayout srl = mDataBinding.refreshLayout;

        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showAchievement(Achievement achievement) {
        mViewModel.setAchievement(achievement);
        mPresenter.loadEvidence();
    }

    @Override
    public void showEvidence(List<Evidence> evidence) {
        EvidenceAdapter adapter = new EvidenceAdapter(evidence, mPresenter);
        mViewModel.setEvidenceAdapter(adapter);
    }
}