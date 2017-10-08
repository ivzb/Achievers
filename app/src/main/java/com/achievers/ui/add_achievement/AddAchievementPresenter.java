package com.achievers.ui.add_achievement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.achievers.utils.PictureUtils;
import com.achievers.validator.Validator;
import com.achievers.validator.contracts.BaseValidation;
import com.achievers.validator.rules.NotNullRule;
import com.achievers.validator.rules.StringLengthRule;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddAchievementPresenter
        extends AbstractPresenter<AddAchievementContract.View>
        implements AddAchievementContract.Presenter {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    @NonNull private final AchievementsDataSource mAchievementsDataSource;
    @NonNull private final FilesDataSource mFilesDataSource;

    private String mImageFilePath;
    private int mTargetWidth;

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
    public void clickTakePicture(int targetWidth) {
        mTargetWidth = targetWidth;

        java.io.File photoFile = null;

        try {
            photoFile = PictureUtils.createFile(mContext);
            mImageFilePath = photoFile.getAbsolutePath();
        } catch (IOException ex) {
            mView.showErrorMessage("Could not take picture. Please try again.");
        }

        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(mContext,
                    "com.achievers.fileprovider",
                    photoFile);

            mView.takePicture(photoURI, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void clickChoosePicture() {
        mView.choosePicture("image/*", REQUEST_IMAGE_PICK);
    }

    @Override
    public void resultForPicture(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = null;

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                bitmap = BitmapFactory.decodeFile(mImageFilePath);

                try { // rotate
                    ExifInterface exif = new ExifInterface(mImageFilePath);

                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    bitmap = PictureUtils.rotate(bitmap, orientation);
                } catch (IOException e) {
                    mView.showErrorMessage("Could not rotate image.");
                }
            }

            if (requestCode == REQUEST_IMAGE_PICK) {
                try {
                    Uri imageUri = data.getData();

                    if (imageUri == null) {
                        throw new FileNotFoundException();
                    }

                    InputStream imageStream = mContext
                            .getContentResolver()
                            .openInputStream(imageUri);

                    bitmap = BitmapFactory.decodeStream(imageStream);
                } catch (FileNotFoundException e) {
                    mView.showErrorMessage("Error occurred. Please try again.");
                }
            }

            if (bitmap != null) {
                Bitmap scaledBitmap = PictureUtils.scale(bitmap, mTargetWidth);
                mView.showImage(scaledBitmap);

                // todo: show uploading indicator

                uploadImage(bitmap, new SaveCallback<String>() {
                    @Override
                    public void onSuccess(String imageUrl) {
                        if (!mView.isActive()) return;

                        // todo: hide uploading indicator

                        mView.setImageUrl(imageUrl);
                    }

                    @Override
                    public void onFailure(String message) {
                        if (!mView.isActive()) return;

                        // todo: hide uploading indicator

                        mView.showErrorMessage("Could not upload image");
                    }
                });
            }
        }
    }

    @Override
    public void saveAchievement(
            String title,
            String description,
            String imageUrl,
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
                        imageUrl,
                        new NotNullRule())
                .validate();

        if (!validation.isValid()) {
            mView.showErrorMessage(validation.getFirstError());
            return;
        }

        Achievement achievement = new Achievement(title, description, imageUrl, involvement, new Date());

        mAchievementsDataSource.saveAchievement(achievement, new SaveCallback<Long>() {
            @Override
            public void onSuccess(Long id) {
                if (!mView.isActive()) return;

                if (id == null) {
                    mView.showErrorMessage("Error occurred while saving achievement.");
                    return;
                }

                mView.finish();
            }

            @Override
            public void onFailure(String message) {
                if (!mView.isActive()) return;

                mView.showErrorMessage(message);
            }
        });
    }

    @Override
    public void loadInvolvements() {
        List<Involvement> involvement = Arrays.asList(Involvement.values());
        mView.showInvolvement(involvement);
    }

    private void uploadImage(Bitmap bitmap, SaveCallback<String> callback) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        File uploadFile = new File(byteArray, "image/jpeg");

        mFilesDataSource.storeFile(uploadFile, callback);
    }
}