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
import com.achievers.utils.FileUtils;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewActionHandler;
import com.achievers.utils.ui.multimedia.players.PhotoMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.VideoMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.VoiceMultimediaPlayer;
import com.achievers.validator.Validator;
import com.achievers.validator.contracts.BaseValidation;
import com.achievers.validator.rules.NotNullRule;
import com.achievers.validator.rules.StringLengthRule;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import static com.achievers.utils.FileUtils.FileType.Picture;
import static com.achievers.utils.Preconditions.checkNotNull;

public class AddEvidencePresenter
        extends AbstractPresenter<AddEvidenceContract.View>
        implements AddEvidenceContract.Presenter {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int REQUEST_VIDEO_CAPTURE = 3;
    private static final int REQUEST_VOICE_CAPTURE = 4;

    private final SimpleExoPlayer mExoPlayer;

    AddEvidencePresenter(
            @NonNull Context context,
            @NonNull AddEvidenceContract.View view,
            @NonNull SimpleExoPlayer exoPlayer) {

        checkNotNull(context);
        checkNotNull(view);

        mContext = context;
        mView = view;
        mExoPlayer = exoPlayer;
    }

    @Override
    public void start() {

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
    public void clickTakeVideo() {
        if (!mView.isActive()) return;

        mView.takeVideo(REQUEST_VIDEO_CAPTURE);
    }

    @Override
    public void clickRecordVoice() {
        if (!mView.isActive()) return;

        mView.recordVoice(REQUEST_VOICE_CAPTURE);
    }

    @Override
    public void deliverMultimedia(
            int requestCode,
            int resultCode,
            Intent data,
            BaseMultimediaViewActionHandler actionHandler) {

        if (!mView.isActive()) return;

        try {
            if (resultCode != Activity.RESULT_OK) throw new IllegalArgumentException();

            Uri uri = data.getData();

            if (uri == null) {
                throw new FileNotFoundException();
            }

            BaseMultimediaPlayer player;

            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    player = new PhotoMultimediaPlayer(actionHandler);
                    break;
                case REQUEST_IMAGE_PICK:
                    player = new VoiceMultimediaPlayer(
                            actionHandler,
                            mContext,
                            mExoPlayer,
                            uri);
                    break;
                case REQUEST_VIDEO_CAPTURE:
                    player = new VideoMultimediaPlayer(
                            actionHandler,
                            mContext,
                            mExoPlayer,
                            uri);
                    break;
                case REQUEST_VOICE_CAPTURE:
                    player = new VoiceMultimediaPlayer(
                            actionHandler,
                            mContext,
                            mExoPlayer,
                            uri);
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            mView.showLoadingMultimedia(false);
            mView.showMultimedia(uri, player);
        } catch (NullPointerException | IllegalArgumentException | FileNotFoundException e) {
            mView.finish();
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