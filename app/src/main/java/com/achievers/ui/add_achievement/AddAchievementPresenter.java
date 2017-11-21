package com.achievers.ui.add_achievement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.achievers.R;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Involvement;
import com.achievers.data.source.involvements.InvolvementsDataSource;
import com.achievers.ui._base.AbstractPresenter;
import com.achievers.ui.add_achievement.AddAchievementContract.Presenter;
import com.achievers.utils.files.FileUtils;
import com.achievers.validator.Validator;
import com.achievers.validator.contracts.BaseValidation;
import com.achievers.validator.rules.NotNullRule;
import com.achievers.validator.rules.StringLengthRule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;
import static com.achievers.utils.files.FileUtils.FileType.Picture;

public class AddAchievementPresenter
        extends AbstractPresenter<AddAchievementContract.View>
        implements Presenter {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    @NonNull private final InvolvementsDataSource mInvolvementsDataSource;

    AddAchievementPresenter(
            @NonNull Context context,
            @NonNull AddAchievementContract.View view,
            @NonNull InvolvementsDataSource involvementsDataSource) {

        checkNotNull(context);
        checkNotNull(view);
        checkNotNull(involvementsDataSource);

        mContext = context;
        mView = view;
        mInvolvementsDataSource = involvementsDataSource;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadInvolvements() {
        if (!mView.isActive()) return;

        mInvolvementsDataSource.loadInvolvements(new LoadCallback<Involvement>() {
            @Override
            public void onSuccess(List<Involvement> data, int page) {
                if (!mView.isActive()) return;
                mView.showInvolvements(data);
            }

            @Override
            public void onFailure(String message) {
                if (!mView.isActive()) return;
                mView.showErrorMessage("Could not load involvements");
            }
        });
    }

    @Override
    public void clickTakePicture() {
        if (!mView.isActive()) return;

        java.io.File photoFile = null;

        try {
            photoFile = FileUtils.createFile(mContext, new Date(), Picture);
        } catch (IOException ex) {
            mView.showErrorMessage("Could not take picture. Please try again.");
        }

        if (photoFile != null) {
            Uri capturedImageUri = FileProvider.getUriForFile(mContext,
                    "com.achievers.fileprovider",
                    photoFile);

            mView.takePicture(capturedImageUri, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void clickChoosePicture() {
        if (!mView.isActive()) return;

        mView.choosePicture("image/*", REQUEST_IMAGE_PICK);
    }

    @Override
    public void clickDiscardPicture() {
        if (!mView.isActive()) return;

        mView.showPicture(null);
    }

    @Override
    public void deliverPicture(int requestCode, int resultCode, Intent data) {
        if (!mView.isActive()) return;

        try {
            if (resultCode != Activity.RESULT_OK) throw new IllegalArgumentException();

            Uri imageUri = null;

            if (requestCode == REQUEST_IMAGE_CAPTURE || requestCode == REQUEST_IMAGE_PICK) {
                imageUri = data.getData();
            }

            if (imageUri == null) {
                throw new FileNotFoundException();
            }

            mView.showPictureLoading(true);
            mView.showPicture(imageUri);
        } catch (NullPointerException | IllegalArgumentException | FileNotFoundException e) {
            mView.showErrorMessage("Did not select picture. Try again?");
        }
    }

    @Override
    public void pictureLoaded() {
        if (!mView.isActive()) return;

        mView.showPictureLoading(false);
    }

    @Override
    public void saveAchievement(
            String title,
            String description,
            Uri pictureUri,
            Involvement involvement,
            int involvementSelectedPosition) {

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
                        R.string.picture,
                        pictureUri,
                        new NotNullRule())
                .validate();

        if (!validation.isValid()) {
            mView.showErrorMessage(validation.getFirstError());
            return;
        }

        Achievement achievement = new Achievement(
                title,
                description,
                involvement,
                pictureUri);

        achievement.setInvolvementPosition(involvementSelectedPosition);

        mView.upload(achievement);
        mView.finish();
    }
}