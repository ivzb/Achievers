package com.achievers.ui.add_achievement;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.achievers.BuildConfig;
import com.achievers.R;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.File;
import com.achievers.data.entities.Involvement;
import com.achievers.databinding.AddAchievementFragBinding;
import com.achievers.ui.add_achievement.Adapters.InvolvementRecyclerViewAdapter;
import com.achievers.ui._base.AbstractFragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddAchievementFragment
        extends AbstractFragment<AddAchievementContract.Presenter, AddAchievementContract.ViewModel>
        implements AddAchievementContract.View {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int REQUEST_PERMISSION = 3;

    private AddAchievementFragBinding mViewDataBinding;

    private String mImageFilePath;

    public AddAchievementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getInvolvements();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab = getActivity().findViewById(R.id.fabAddAchievement);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mViewDataBinding.etTitle.getText().toString();
                String description = mViewDataBinding.etDescription.getText().toString();
                String imageUrl = "";
                Integer categoryId = null;
                Involvement involvement = mViewModel.getInvolvementsAdapter().getSelectedInvolvement();

//                Achievement achievement = new Achievement(title, description, imageUrl, categoryId, involvement);

//                if (!mPresenter.validateAchievement(achievement)) {
//                    // TODO: compose proper message
//                    showInvalidAchievementMessage("Please fill required fields.");
//                    return;
//                }
//
//                // hide keyboard before finish so as not to break previous Activity layout
//                View view = getActivity().getCurrentFocus();
//
//                if (view != null) {
//                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                }
//
//                mPresenter.saveAchievement(achievement);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (mViewDataBinding == null) {
            mViewDataBinding = AddAchievementFragBinding.inflate(inflater, container, false);
        }

        mViewDataBinding.setViewModel(mViewModel);

        mViewDataBinding.btnTakePicture.setOnClickListener(this.takePictureListener);
        mViewDataBinding.btnChoosePicture.setOnClickListener(this.choosePictureListener);

        setHasOptionsMenu(true);
        // Fragment is retained simply to persist the edits after rotation.
        setRetainInstance(true);

        return mViewDataBinding.getRoot();
    }

    @Override
    public void onRequestPermissionsResult(
            final int requestCode,
            @NonNull final String[] permissions,
            @NonNull final int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture();
            } else {
                Snackbar.make(mViewDataBinding.btnChoosePicture, "Please grant access.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = null;

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;

                bitmap = BitmapFactory.decodeFile(mImageFilePath, options);
            }

            if (requestCode == REQUEST_IMAGE_PICK) {
                try {
                    Uri imageUri = data.getData();

                    InputStream imageStream = getActivity()
                            .getContentResolver()
                            .openInputStream(imageUri);

                    bitmap = BitmapFactory.decodeStream(imageStream);
                } catch (FileNotFoundException e) {
                    Snackbar.make(mViewDataBinding.btnChoosePicture, "Error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            if (bitmap != null) {
                // todo: show uploading indicator
                mViewDataBinding.ivPicture.setImageBitmap(bitmap);

                mPresenter.uploadImage(bitmap, new SaveCallback<File>() {
                    @Override
                    public void onSuccess(File data) {
                        // todo: hide uploading indicator
                    }

                    @Override
                    public void onFailure(String message) {
                        // todo: retry button
                    }
                });
            }
        }
    }

    @Override
    public void finish() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showInvolvement(List<Involvement> involvement) {
        mViewModel.setInvolvementsAdapter(new InvolvementRecyclerViewAdapter(
                getContext(),
                involvement));
    }

    public View.OnClickListener takePictureListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int permissionResult = ContextCompat.checkSelfPermission(
                    getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    permissionResult != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                        REQUEST_PERMISSION);

                return;
            }

            takePicture();
        }
    };

    private void takePicture() {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            mImageFilePath =
                    Environment.getExternalStorageDirectory().toString() +
                    "/Android/data/com.achievers/Image-" + timeStamp + ".png";

            java.io.File imageFile = new java.io.File(mImageFilePath);

            if (!imageFile.exists()) {
                imageFile.getParentFile().mkdirs();
                imageFile.createNewFile();
            }

            Uri imageFileUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", imageFile);

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);

            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        } catch (IOException e) {
            showErrorMessage("Could not take picture. Please try again.");
        }
    }

    public View.OnClickListener choosePictureListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, REQUEST_IMAGE_PICK);
        }
    };

    @Override
    public boolean isActive() {
        return isAdded();
    }
}