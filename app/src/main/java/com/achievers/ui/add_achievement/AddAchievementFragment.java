package com.achievers.ui.add_achievement;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Involvement;
import com.achievers.databinding.AddAchievementFragBinding;
import com.achievers.ui._base.AbstractFragment;
import com.achievers.ui.add_achievement.Adapters.InvolvementAdapter;

import java.util.List;

public class AddAchievementFragment
        extends AbstractFragment<AddAchievementContract.Presenter, AddAchievementContract.ViewModel, AddAchievementFragBinding>
        implements AddAchievementContract.View<AddAchievementFragBinding>, View.OnClickListener {


    public AddAchievementFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_achievement_frag, container, false);

        mDataBinding = AddAchievementFragBinding.bind(view);
        mDataBinding.setViewModel(mViewModel);

        mDataBinding.btnTakePicture.setOnClickListener(mTakePictureListener);
        mDataBinding.btnChoosePicture.setOnClickListener(mChoosePictureListener);

        setupFab();

        return mDataBinding.getRoot();
    }

    @Override
    public void onRequestPermissionsResult(
            final int requestCode,
            @NonNull final String[] permissions,
            @NonNull final int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPresenter.requestPermission(requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.resultPermission(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        String title = mDataBinding.etTitle.getText().toString();
        String description = mDataBinding.etDescription.getText().toString();
        String imageUrl = "";
        Involvement involvement = mViewModel.getInvolvementsAdapter().getSelectedInvolvement();

        mPresenter.saveAchievement(title, description, imageUrl, involvement);
    }

    @Override
    public void takePicture(Uri uri, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        PackageManager packageManager = getActivity().getPackageManager();

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void choosePicture(String type, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(type);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void showInvolvement(List<Involvement> involvement) {
        mViewModel.setInvolvementsAdapter(new InvolvementAdapter(
                getContext(),
                involvement));
    }

    @Override
    public void showImage(Bitmap bitmap) {
        mDataBinding.ivPicture.setImageBitmap(bitmap);
    }

    @Override
    public void finish() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    private View.OnClickListener mTakePictureListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int permission = ContextCompat.checkSelfPermission(
                    getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE);

            mPresenter.clickTakePicture(permission);
        }
    };

    private View.OnClickListener mChoosePictureListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mPresenter.clickChoosePicture();
        }
    };

    private void setupFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fabAddAchievement);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(this);
    }
}