package com.achievers.ui.add_achievement;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.media.ExifInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.achievers.R;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.File;
import com.achievers.data.entities.Involvement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.files.FilesDataSource;
import com.achievers.ui._base.AbstractPresenter;
import com.achievers.ui.add_achievement.AddAchievementContract.Presenter;
import com.achievers.utils.PictureUtils;
import com.achievers.validator.Validator;
import com.achievers.validator.contracts.BaseValidation;
import com.achievers.validator.rules.NotNullRule;
import com.achievers.validator.rules.StringLengthRule;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddAchievementPresenter
        extends AbstractPresenter<AddAchievementContract.View>
        implements Presenter {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    @NonNull private final AchievementsDataSource mAchievementsDataSource;
    @NonNull private final FilesDataSource mFilesDataSource;

    private Uri mCapturedImageUri;

    AddAchievementPresenter(
            @NonNull Context context,
            @NonNull AddAchievementContract.View view,
            @NonNull AchievementsDataSource achievementsDataSource,
            @NonNull FilesDataSource filesDataSource) {

        mContext = context;
        mView = view;

        mAchievementsDataSource = achievementsDataSource;
        mFilesDataSource = filesDataSource;
    }

    @Override
    public void start() {
        loadInvolvements();
    }

    @Override
    public void loadInvolvements() {
        List<Involvement> involvement = Arrays.asList(Involvement.values());
        mView.showInvolvement(involvement);
    }

    @Override
    public void clickTakePicture() {
        java.io.File photoFile = null;

        try {
            photoFile = PictureUtils.createFile(mContext);
        } catch (IOException ex) {
            mView.showErrorMessage("Could not take picture. Please try again.");
        }

        if (photoFile != null) {
            mCapturedImageUri = FileProvider.getUriForFile(mContext,
                    "com.achievers.fileprovider",
                    photoFile);

            mView.takePicture(mCapturedImageUri, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void clickChoosePicture() {
        mView.choosePicture("image/*", REQUEST_IMAGE_PICK);
    }

    @Override
    public void deliverPicture(int requestCode, int resultCode, Intent data) {
        if (!mView.isActive()) return;

        if (resultCode == Activity.RESULT_OK) {
            try {
                Uri imageUri = null;

                if (requestCode == REQUEST_IMAGE_CAPTURE) {
                    imageUri = mCapturedImageUri;
                } else if (requestCode == REQUEST_IMAGE_PICK) {
                    imageUri = data.getData();
                }

                if (imageUri == null) {
                    throw new FileNotFoundException();
                }

                mView.showPicture(imageUri);
            } catch (FileNotFoundException e) {
                mView.showErrorMessage("Error occurred. Please try again.");
            }
        }
    }

    @Override
    public void saveAchievement(
            String title,
            String description,
            Uri imageUri,
            Involvement involvement) {

        if (!mView.isActive()) return;

        mView.hideKeyboard();

        BaseValidation validation = new Validator(mContext)
                .addProperty(
                        R.string.title,
                        title,
                        new StringLengthRule(5, 100))
                .addProperty(
                        R.string.description,
                        description,
                        new StringLengthRule(3, 255))
                .addProperty(
                        R.string.involvement,
                        involvement,
                        new NotNullRule())
                .addProperty(
                        R.string.image,
                        imageUri,
                        new NotNullRule())
                .validate();

        if (!validation.isValid()) {
            mView.showErrorMessage(validation.getFirstError());
            return;
        }

        mView.finish();

        // todo: save image in background and after that save achievement


//        if (bitmap != null) {
//            // todo: show uploading indicator
//
//            uploadImage(bitmap, new SaveCallback<String>() {
//                @Override
//                public void onSuccess(String imageUrl) {
//                    if (!mView.isActive()) return;
//
//                    // todo: hide uploading indicator
//
////                        mView.setImageUrl(imageUrl);
//                }
//
//                @Override
//                public void onFailure(String message) {
//                    if (!mView.isActive()) return;
//
//                    // todo: hide uploading indicator
//
//                    mView.showErrorMessage("Could not upload image");
//                }
//            });
//        }

//        Achievement achievement = new Achievement(title, description, imageUrl, involvement, new Date());

//        mAchievementsDataSource.saveAchievement(achievement, new SaveCallback<Long>() {
//            @Override
//            public void onSuccess(Long id) {
//                if (!mView.isActive()) return;
//
//                if (id == null) {
//                    mView.showErrorMessage("Error occurred while saving achievement.");
//                    return;
//                }
//
//                mView.finish();
//            }
//
//            @Override
//            public void onFailure(String message) {
//                if (!mView.isActive()) return;
//
//                mView.showErrorMessage(message);
//            }
//        });
    }

    private void uploadImage(Uri imageUri, SaveCallback<String> callback) {

        Bitmap bitmap;

        try {
            InputStream imageStream = mContext
                    .getContentResolver()
                    .openInputStream(imageUri);

            bitmap = BitmapFactory.decodeStream(imageStream);
        } catch (FileNotFoundException e) {
            callback.onFailure("Could not load image.");
            return;
        }

        if (bitmap == null) {
            callback.onFailure("Image not found.");
            return;
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        File uploadFile = new File(byteArray, "image/jpeg");

        mFilesDataSource.storeFile(uploadFile, callback);
    }
}