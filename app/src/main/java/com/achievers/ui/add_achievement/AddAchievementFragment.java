package com.achievers.ui.add_achievement;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Involvement;
import com.achievers.databinding.AddAchievementFragBinding;
import com.achievers.sync.UploadAchievementIntentService;
import com.achievers.ui._base.AbstractFragment;
import com.achievers.ui._base.adapters.SelectableAdapter;

import org.parceler.Parcels;

import java.util.List;

import static com.achievers.sync.UploadAchievementIntentService.ACHIEVEMENT_EXTRA;

public class AddAchievementFragment
        extends AbstractFragment<AddAchievementContract.Presenter, AddAchievementContract.ViewModel, AddAchievementFragBinding>
        implements AddAchievementContract.View<AddAchievementFragBinding> {

    public static final String TITLE_KEY = "title";
    public static final String DESCRIPTION_KEY = "description";
    public static final String INVOLVEMENTS_ADAPTER_SELECTED_POSITION_KEY = "involvements_adapter_selected_position";
    private static final String INVOLVEMENTS_LAYOUT_MANAGER_KEY = "involvements_layout_manager";
    public static final String PICTURE_KEY = "picture";
    private static final String PICTURE_LOADING_KEY = "picture_loading";
    private static final String CAPTURE_PICTURE_KEY = "capture_picture";

    private Parcelable mLayoutManagerState;
    private int mAdapterSelectedPosition;

    private Uri mCapturedPictureUri;
    private AddAchievementContract.ActionHandler mActionHandler;

    public AddAchievementFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.add_achievement_frag, container, false);

        mDataBinding = AddAchievementFragBinding.bind(view);
        mDataBinding.setViewModel(mViewModel);

        mActionHandler = new AddAchievementActionHandler(getContext(), mPresenter);
        mDataBinding.setActionHandler(mActionHandler);

        mDataBinding.btnTakePicture.setOnClickListener(mTakePictureListener);
        mDataBinding.btnChoosePicture.setOnClickListener(mChoosePictureListener);
        mDataBinding.ivPicture.setOnClickListener(mDeletePictureListener);

        if (getArguments() != null) {
            restoreState(getArguments());
        }

        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }

        mPresenter.loadInvolvements();

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
                String description = mViewModel.getDescription();
                Uri imageUri = mViewModel.getPictureUri();
                Involvement involvement = mViewModel.getInvolvementsAdapter().getSelection();
                mAdapterSelectedPosition = mViewModel.getInvolvementsAdapter().getSelectedPosition();

                mPresenter.saveAchievement(title, description, imageUri, involvement, mAdapterSelectedPosition);
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

        int adapterSelectedPosition = mViewModel.getInvolvementsAdapter().getSelectedPosition();
        outState.putInt(INVOLVEMENTS_ADAPTER_SELECTED_POSITION_KEY, adapterSelectedPosition);

        Parcelable pictureState = Parcels.wrap(mViewModel.getPictureUri());
        outState.putParcelable(PICTURE_KEY, pictureState);

        outState.putBoolean(PICTURE_LOADING_KEY, mViewModel.isPictureLoading());

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
        adapter.setSelectedPosition(mAdapterSelectedPosition);

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
        mViewModel.setPictureUri(uri);
    }

    @Override
    public void showPictureLoading(boolean loading) {
        mViewModel.setPictureLoading(loading);
    }

    @Override
    public void upload(Achievement achievement) {
        Context context = getContext();

        Intent intent = new Intent(context, UploadAchievementIntentService.class);
        intent.putExtra(ACHIEVEMENT_EXTRA, Parcels.wrap(achievement));
        context.startService(intent);
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

    private void restoreState(Bundle state) {
        if (state.containsKey(TITLE_KEY)) {
            mViewModel.setTitle(state.getString(TITLE_KEY));
        }

        if (state.containsKey(DESCRIPTION_KEY)) {
            mViewModel.setDescription(state.getString(DESCRIPTION_KEY));
        }

        if (state.containsKey(INVOLVEMENTS_LAYOUT_MANAGER_KEY)) {
            mLayoutManagerState = state.getParcelable(INVOLVEMENTS_LAYOUT_MANAGER_KEY);
        }

        if (state.containsKey(INVOLVEMENTS_ADAPTER_SELECTED_POSITION_KEY)) {
            mAdapterSelectedPosition = state.getInt(INVOLVEMENTS_ADAPTER_SELECTED_POSITION_KEY);
        }

        if (state.containsKey(PICTURE_KEY)) {
            Uri imageUri = Parcels.unwrap(state.getParcelable(PICTURE_KEY));
            showPicture(imageUri);
        }

        if (state.containsKey(PICTURE_LOADING_KEY)) {
            mViewModel.setPictureLoading(state.getBoolean(PICTURE_LOADING_KEY));
        }

        if (state.containsKey(CAPTURE_PICTURE_KEY)) {
            mCapturedPictureUri = Parcels.unwrap(state.getParcelable(CAPTURE_PICTURE_KEY));
        }
    }
}