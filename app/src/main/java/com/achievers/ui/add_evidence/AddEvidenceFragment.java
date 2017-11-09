package com.achievers.ui.add_evidence;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Evidence;
import com.achievers.databinding.AddEvidenceFragBinding;
import com.achievers.ui._base.AbstractFragment;
import com.achievers.ui.voice_recording.VoiceRecordingActivity;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;

public class AddEvidenceFragment
        extends AbstractFragment<AddEvidenceContract.Presenter, AddEvidenceContract.ViewModel, AddEvidenceFragBinding>
        implements AddEvidenceContract.View<AddEvidenceFragBinding> {

    private Uri mCapturedPictureUri;

    public AddEvidenceFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.add_evidence_frag, container, false);

        mDataBinding = AddEvidenceFragBinding.bind(view);
        mDataBinding.setViewModel(mViewModel);

        switch (mViewModel.getMultimediaType()) {
            case Photo:
                mPresenter.clickTakePicture();
                break;
            case Video:
                mPresenter.clickTakeVideo();
                break;
            case Voice:
                mPresenter.clickRecordVoice();
                break;
        }

        return mDataBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                String title = mViewModel.getTitle();

                mPresenter.saveEvidence(title, mCapturedPictureUri);
                break;
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCapturedPictureUri != null) {
            data = new Intent();
            data.setData(mCapturedPictureUri);
        }

        mPresenter.deliverMultimedia(requestCode, resultCode, data, mDataBinding.mvEvidence);
    }

    @Override
    public void onPause() {
        super.onPause();

        mDataBinding.mvEvidence.release();
    }

    @Override
    public void takePicture(Uri uri, int requestCode) {
        mCapturedPictureUri = uri;

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        PackageManager packageManager = getActivity().getPackageManager();

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void takeVideo(int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        PackageManager packageManager = getActivity().getPackageManager();

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void recordVoice(int requestCode) {
        Intent intent = new Intent(getContext(), VoiceRecordingActivity.class);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void showMultimedia(Uri uri, BaseMultimediaPlayer player) {
        mViewModel.setMultimediaUri(uri);

        // todo: catch if builder throws null pointer and show message
        mDataBinding.mvEvidence.builder(mViewModel.getMultimediaType())
                .withUri(uri)
                .withPlayer(player)
                .build();
    }

    @Override
    public void showLoadingMultimedia(boolean loading) {
        mViewModel.setMultimediaLoading(loading);
    }

    @Override
    public void upload(Evidence evidence) {
        Context context = getContext();

        // todo
//        Intent intent = new Intent(context, UploadEvidenceIntentService.class);
//        intent.putExtra(ACHIEVEMENT_EXTRA, Parcels.wrap(evidence));
//        context.startService(intent);
    }

    @Override
    public void finish() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }
}