package com.achievers.ui.add_achievement;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Involvement;
import com.achievers.databinding.AddAchievementFragBinding;
import com.achievers.ui._base.AbstractFragment;
import com.achievers.ui._base.adapters.SelectableAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.parceler.Parcels;

import java.util.List;

public class AddAchievementFragment
        extends AbstractFragment<AddAchievementContract.Presenter, AddAchievementContract.ViewModel, AddAchievementFragBinding>
        implements AddAchievementContract.View<AddAchievementFragBinding>, RequestListener<Drawable> {

    private static final String TITLE_KEY = "title";
    private static final String DESCRIPTION_KEY = "description";
    private static final String INVOLVEMENTS_ADAPTER_KEY = "involvements_adapter";
    private static final String INVOLVEMENTS_LAYOUT_MANAGER_KEY = "involvements_layout_manager";
    private static final String PICTURE_KEY = "picture";
    private static final String CAPTURE_PICTURE_KEY = "capture_picture";

    private Parcelable mLayoutManagerState;
    private Parcelable mAdapterState;

    private Uri mCapturedPictureUri;

    public AddAchievementFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.add_achievement_frag, container, false);

        mDataBinding = AddAchievementFragBinding.bind(view);
        mDataBinding.setViewModel(mViewModel);

        mDataBinding.btnTakePicture.setOnClickListener(mTakePictureListener);
        mDataBinding.btnChoosePicture.setOnClickListener(mChoosePictureListener);
        mDataBinding.ivPicture.setOnClickListener(mDeletePictureListener);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(TITLE_KEY)) {
                mViewModel.setTitle(savedInstanceState.getString(TITLE_KEY));
            }

            if (savedInstanceState.containsKey(DESCRIPTION_KEY)) {
                mViewModel.setDescription(savedInstanceState.getString(DESCRIPTION_KEY));
            }

            if (savedInstanceState.containsKey(INVOLVEMENTS_LAYOUT_MANAGER_KEY)) {
                mLayoutManagerState = savedInstanceState.getParcelable(INVOLVEMENTS_LAYOUT_MANAGER_KEY);
            }

            if (savedInstanceState.containsKey(INVOLVEMENTS_ADAPTER_KEY)) {
                mAdapterState = savedInstanceState.getParcelable(INVOLVEMENTS_ADAPTER_KEY);
            }

            if (savedInstanceState.containsKey(PICTURE_KEY)) {
                Uri imageUri = Parcels.unwrap(savedInstanceState.getParcelable(PICTURE_KEY));
                showPicture(imageUri);
            }

            if (savedInstanceState.containsKey(CAPTURE_PICTURE_KEY)) {
                mCapturedPictureUri = Parcels.unwrap(savedInstanceState.getParcelable(CAPTURE_PICTURE_KEY));
            }
        }

        mPresenter.loadInvolvements();

        return mDataBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_achievement_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                String title = mViewModel.getTitle();
                String description = mViewModel.getDescription();
                Uri imageUri = mViewModel.getImageUri();
                Involvement involvement = mViewModel.getInvolvementsAdapter().getSelected();

                mPresenter.saveAchievement(title, description, imageUri, involvement);
                break;
        }

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(TITLE_KEY, mDataBinding.etTitle.getText().toString());
        outState.putString(DESCRIPTION_KEY, mDataBinding.etDescription.getText().toString());

        Parcelable layoutManagerState = mDataBinding.rvInvolvements.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(INVOLVEMENTS_LAYOUT_MANAGER_KEY, layoutManagerState);

        Parcelable adapterState = mViewModel.getInvolvementsAdapter().onSaveInstanceState();
        outState.putParcelable(INVOLVEMENTS_ADAPTER_KEY, adapterState);

        Parcelable pictureState = Parcels.wrap(mViewModel.getImageUri());
        outState.putParcelable(PICTURE_KEY, pictureState);

        Parcelable capturedPicture = Parcels.wrap(mCapturedPictureUri);
        outState.putParcelable(CAPTURE_PICTURE_KEY, capturedPicture);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCapturedPictureUri != null) {
            data = new Intent();
            data.setData(mCapturedPictureUri);
        }

        mPresenter.deliverPicture(requestCode, resultCode, data);
    }

    @Override
    public void showInvolvements(List<Involvement> involvements) {
        SelectableAdapter<Involvement> adapter = new SelectableAdapter<>(getContext(), involvements);
        adapter.onRestoreInstanceState(mAdapterState);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.onRestoreInstanceState(mLayoutManagerState);

        mViewModel.setInvolvements(adapter, layoutManager);
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
    public void choosePicture(String type, int requestCode) {
        mCapturedPictureUri = null;

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(type);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void showPicture(Uri uri) {
        if (uri == null) {
            mViewModel.setImageLoading(false);
            mViewModel.setImageUri(null);
            return;
        }

        mViewModel.setImageLoading(true);
        mViewModel.setImageUri(uri);

        // todo: extract this in AdapterSetters, passing ActionHandler
        // which triggers .imageLoading() from presenter
        Glide.with(getContext())
                .load(uri)
                .listener(this)
                .into(mDataBinding.ivPicture);
    }

    @Override
    public void finish() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public boolean onLoadFailed(
            @Nullable GlideException e,
            Object model, Target<Drawable> target,
            boolean isFirstResource) {

        showPicture(null);
        showErrorMessage("Could not load image.");

        return false;
    }

    @Override
    public boolean onResourceReady(
            Drawable resource,
            Object model,
            Target<Drawable> target,
            DataSource dataSource,
            boolean isFirstResource) {

        mViewModel.setImageLoading(false);

        return false;
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

    private View.OnClickListener mDeletePictureListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage(R.string.dialog_discard_picture)
                    .setPositiveButton(R.string.discard, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mPresenter.clickDiscardPicture();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };
}