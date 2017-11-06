package com.achievers.ui.add_evidence;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.achievers.R;
import com.achievers.data.entities.Evidence;
import com.achievers.ui._base.AbstractPresenter;
import com.achievers.utils.PictureUtils;
import com.achievers.validator.Validator;
import com.achievers.validator.contracts.BaseValidation;
import com.achievers.validator.rules.NotNullRule;
import com.achievers.validator.rules.StringLengthRule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import static com.achievers.utils.Preconditions.checkNotNull;

public class AddEvidencePresenter
        extends AbstractPresenter<AddEvidenceContract.View>
        implements AddEvidenceContract.Presenter {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    AddEvidencePresenter(
            @NonNull Context context,
            @NonNull AddEvidenceContract.View view) {

        checkNotNull(context);
        checkNotNull(view);

        mContext = context;
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void clickTakePicture() {
        if (!mView.isActive()) return;

        java.io.File photoFile = null;

        try {
            photoFile = PictureUtils.createFile(mContext, new Date());
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
    public void pictureLoaded(boolean isSuccessful) {
        if (!mView.isActive()) return;

        mView.showPictureLoading(false);

        if (!isSuccessful) {
            mView.showPicture(null);
            mView.showErrorMessage("Could not load image.");
        }
    }

    @Override
    public void saveEvidence(
            String title,
            Uri pictureUri) {

        if (!mView.isActive()) return;

        mView.hideKeyboard();

        BaseValidation validation = new Validator(mContext)
                .addProperty(
                        R.string.title,
                        title,
                        new StringLengthRule(5, 100))
                .addProperty(
                        R.string.picture,
                        pictureUri,
                        new NotNullRule())
                .validate();

        if (!validation.isValid()) {
            mView.showErrorMessage(validation.getFirstError());
            return;
        }

        Evidence evidence = new Evidence(5);

        mView.upload(evidence);
        mView.finish();
    }
}