package com.achievers.ui.add_achievement;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;

import com.achievers.BuildConfig;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.File;
import com.achievers.data.entities.Involvement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.files.FilesDataSource;
import com.achievers.ui._base.AbstractPresenter;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddAchievementPresenter
        extends AbstractPresenter<AddAchievementContract.View>
        implements AddAchievementContract.Presenter {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int REQUEST_PERMISSION = 3;

    @NonNull private final Activity mActivity;

    @NonNull private final AchievementsDataSource mAchievementsDataSource;
    @NonNull private final FilesDataSource mFilesDataSource;

    private String mImageFilePath;

    AddAchievementPresenter(
            @NonNull Activity activity,
            @NonNull AddAchievementContract.View view,
            @NonNull AchievementsDataSource achievementsDataSource,
            @NonNull FilesDataSource filesDataSource) {

        mActivity = activity;
        mContext = activity;

        mView = view;

        mAchievementsDataSource = achievementsDataSource;
        mFilesDataSource = filesDataSource;
    }

    @Override
    public void start() {
        getInvolvements();
    }

    @Override
    public void requestPermission(int requestCode, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture();
            } else {
                mView.showErrorMessage("Please grant access.");
            }
        }
    }

    @Override
    public void resultPermission(int requestCode, int resultCode, Intent data) {
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
                // todo: show uploading indicator
                mView.showImage(bitmap);

                uploadImage(bitmap, new SaveCallback<File>() {
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
    public void clickTakePicture(int permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                permission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    mActivity,
                    new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE },
                    REQUEST_PERMISSION);

            return;
        }

        takePicture();
    }

    @Override
    public void clickChoosePicture() {
        mView.choosePicture("image/*", REQUEST_IMAGE_PICK);
    }

    @Override
    public void saveAchievement(String title, String description, String imageUrl, Involvement involvement) {
        Achievement achievement = new Achievement(title, description, imageUrl, involvement, new Date());

        // TODO: validate achievement

        mView.hideKeyboard();

        mAchievementsDataSource.saveAchievement(achievement, new SaveCallback<Achievement>() {
            @Override
            public void onSuccess(Achievement result) {
                mView.finish();
            }

            @Override
            public void onFailure(String message) {
                mView.showErrorMessage(message);
            }
        });
    }

    private void takePicture() {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());

            mImageFilePath = String.format(
                    "%s/Android/data/%s/Image-%s.png",
                    Environment.getExternalStorageDirectory().toString(),
                    BuildConfig.APPLICATION_ID,
                    timeStamp);

            java.io.File imageFile = new java.io.File(mImageFilePath);

            if (!imageFile.exists()) {
                imageFile.getParentFile().mkdirs();
                imageFile.createNewFile();
            }

            Uri uri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider", imageFile);

            mView.takePicture(uri, REQUEST_IMAGE_CAPTURE);
        } catch (IOException e) {
            mView.showErrorMessage("Could not take picture. Please try again.");
        }
    }

    @Override
    public void getInvolvements() {
        List<Involvement> involvement = Arrays.asList(Involvement.values());
        mView.showInvolvement(involvement);
    }

    @Override
    public void uploadImage(Bitmap bitmap, SaveCallback<File> callback) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        File uploadFile = new File(byteArray, "image/jpeg");

        mFilesDataSource.storeFile(uploadFile, callback);
    }
}
