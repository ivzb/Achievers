package com.achievers.AddAchievement;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.achievers.AddAchievement.Adapters.InvolvementRecyclerViewAdapter;
import com.achievers.BuildConfig;
import com.achievers.R;
import com.achievers.data.Achievement;
import com.achievers.data.Involvement;
import com.achievers.databinding.AddAchievementFragBinding;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class AddAchievementFragment extends Fragment implements AddAchievementContract.View {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int REQUEST_PERMISSION = 3;

    private AddAchievementContract.Presenter mPresenter;

    private String mImageFilePath;

    private EditText mEtTitle;
    private EditText mEtDescription;
    private RecyclerView mRvInvolvement;
    private Button mBtnTakePicture;
    private Button mBtnChoosePicture;
    private ImageView mIvPicture;

    private AddAchievementFragBinding mViewDataBinding;

    private AddAchievementViewModel mViewModel;

    public static AddAchievementFragment newInstance() {
        return new AddAchievementFragment();
    }

    public AddAchievementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull AddAchievementContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_achievement);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mEtTitle.getText().toString();
                String description = mEtDescription.getText().toString();
                // todo: get id
                String involvement = mViewModel.getInvolvementRecyclerViewAdapter().getSelectedInvolvement().toString();

                Achievement achievement = new Achievement();//title, description, involvement, equipmentId);

                if (!mPresenter.validateAchievement(achievement)) {
                    // TODO: compose proper message
                    showInvalidAchievementMessage("Please fill required fields.");
                    return;
                }

                // hide keyboard before finish so as not to break previous Activity layout
                View view = getActivity().getCurrentFocus();

                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                mPresenter.saveAchievement(achievement);
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

        mEtTitle = mViewDataBinding.etTitle;
        mEtDescription = mViewDataBinding.etDescription;
        mRvInvolvement = mViewDataBinding.rvInvolvement;
        mBtnTakePicture = mViewDataBinding.btnTakePicture;
        mBtnChoosePicture = mViewDataBinding.btnChoosePicture;
        mIvPicture = mViewDataBinding.ivPicture;

        mBtnTakePicture.setOnClickListener(this.takePictureListener);
        mBtnChoosePicture.setOnClickListener(this.choosePictureListener);

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
                Snackbar.make(mBtnChoosePicture, "Please grant access.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;

            Bitmap bitmap = BitmapFactory.decodeFile(mImageFilePath, options);
            mIvPicture.setImageBitmap(bitmap);
        }

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == getActivity().RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);

                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                mIvPicture.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Snackbar.make(mBtnChoosePicture, "Error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void finishActivity() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showInvolvement(List<Involvement> involvement) {
        InvolvementRecyclerViewAdapter involvementAdapter = new InvolvementRecyclerViewAdapter(this.getContext(), involvement);
        this.mViewModel.setInvolvementRecyclerViewAdapter(involvementAdapter);
    }

    @Override
    public void showInvalidAchievementMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setViewModel(AddAchievementViewModel viewModel) {
        mViewModel = viewModel;
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
            showInvalidAchievementMessage("Could not take picture. Please try again.");
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