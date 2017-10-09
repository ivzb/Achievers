package com.achievers.ui.add_achievement;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Involvement;
import com.achievers.databinding.AddAchievementFragBinding;
import com.achievers.ui._base.AbstractFragment;
import com.achievers.ui._base.adapters.SelectableAdapter;
import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.io.File;
import java.util.List;

public class AddAchievementFragment
        extends AbstractFragment<AddAchievementContract.Presenter, AddAchievementContract.ViewModel, AddAchievementFragBinding>
        implements AddAchievementContract.View<AddAchievementFragBinding>, View.OnClickListener {

    private static final String TITLE_KEY = "title";
    private static final String DESCRIPTION_KEY = "description";
    private static final String INVOLVEMENTS_KEY = "involvements";
    private static final String INVOLVEMENTS_LAYOUT_MANAGER_KEY = "involvements_layout_manager";
    private static final String PICTURE_KEY = "picture";

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

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(TITLE_KEY)) {
                String title = savedInstanceState.getString(TITLE_KEY);
                mViewModel.setTitle(title);
            }

            if (savedInstanceState.containsKey(DESCRIPTION_KEY)) {
                String description = savedInstanceState.getString(DESCRIPTION_KEY);
                mViewModel.setDescription(description);
            }

            if (savedInstanceState.containsKey(INVOLVEMENTS_LAYOUT_MANAGER_KEY)) {
                Parcelable state = savedInstanceState.getParcelable(INVOLVEMENTS_LAYOUT_MANAGER_KEY);
                mViewModel.setInvolvementsLayoutManagerState(state);
            }

            if (savedInstanceState.containsKey(INVOLVEMENTS_KEY)) {
                Parcelable state = savedInstanceState.getParcelable(INVOLVEMENTS_KEY);
                mViewModel.setInvolvementsState(state);
            }

            if (savedInstanceState.containsKey(PICTURE_KEY)) {
                Uri imageUri = Parcels.unwrap(savedInstanceState.getParcelable(PICTURE_KEY));
                showPicture(imageUri);
            }
        }

        return mDataBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(TITLE_KEY, mDataBinding.etTitle.getText().toString());
        outState.putString(DESCRIPTION_KEY, mDataBinding.etDescription.getText().toString());

        Parcelable involvementsLayoutManagerState = mDataBinding.rvInvolvement.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(INVOLVEMENTS_LAYOUT_MANAGER_KEY, involvementsLayoutManagerState);

        Parcelable involvementsState = mViewModel.getInvolvementsAdapter().onSaveInstanceState();
        outState.putParcelable(INVOLVEMENTS_KEY, involvementsState);

        Parcelable pictureState = Parcels.wrap(mViewModel.getImageUri());
        outState.putParcelable(PICTURE_KEY, pictureState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.deliverPicture(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        String title = mViewModel.getTitle();
        String description = mViewModel.getDescription();
        Uri imageUri = mViewModel.getImageUri();
        Involvement involvement = mViewModel.getInvolvementsAdapter().getSelected();

        mPresenter.saveAchievement(title, description, imageUri, involvement);
    }

    @Override
    public void showInvolvement(List<Involvement> involvement) {
        SelectableAdapter<Involvement> adapter = new SelectableAdapter<>(getContext(), involvement);

        Parcelable adapterState = mViewModel.getInvolvementsState();

        if (adapterState != null) {
            adapter.onRestoreInstanceState(adapterState);
        }

        mViewModel.setInvolvementsAdapter(adapter);
        mDataBinding.rvInvolvement.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mDataBinding.rvInvolvement.setLayoutManager(layoutManager);

        Parcelable layoutManagerState = mViewModel.getInvolvementsLayoutManagerState();

        if (layoutManagerState != null) {
            layoutManager.onRestoreInstanceState(layoutManagerState);
        }
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
    public void showPicture(Uri uri) {
        mViewModel.setImageUri(uri);

        Glide.with(getContext())
                .load(uri)
                .into(mDataBinding.ivPicture);

        // todo: show progress while loading picture
    }

    @Override
    public void finish() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    private View.OnClickListener mTakePictureListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mPresenter.clickTakePicture();
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