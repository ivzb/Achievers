package com.achievers.ui.add_evidence;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.achievers.R;
import com.achievers.data.entities.Evidence;
import com.achievers.ui._base.AbstractPresenter;
import com.achievers.utils.files.factory.FileFactory;
import com.achievers.utils.ui.multimedia.MultimediaType;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewActionHandler;
import com.achievers.utils.ui.multimedia.players.PhotoMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.VideoMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.VoiceMultimediaPlayer;
import com.achievers.validator.Validator;
import com.achievers.validator.contracts.BaseValidation;
import com.achievers.validator.rules.NotNullRule;
import com.achievers.validator.rules.StringLengthRule;
import com.achievers.validator.rules.ValidIdRule;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.io.FileNotFoundException;
import java.io.IOException;

import static com.achievers.utils.Preconditions.checkNotNull;

public class AddEvidencePresenter
        extends AbstractPresenter<AddEvidenceContract.View>
        implements AddEvidenceContract.Presenter {

    @VisibleForTesting
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    @VisibleForTesting
    public static final int REQUEST_VIDEO_CAPTURE = 2;
    @VisibleForTesting
    public static final int REQUEST_VOICE_CAPTURE = 3;

    private final FileFactory mFileFactory;
    private final SimpleExoPlayer mExoPlayer;

    AddEvidencePresenter(
            @NonNull Context context,
            @NonNull AddEvidenceContract.View view,
            @NonNull FileFactory fileFactory,
            @NonNull SimpleExoPlayer exoPlayer) {

        checkNotNull(context);
        checkNotNull(view);
        checkNotNull(fileFactory);
        checkNotNull(exoPlayer);

        mContext = context;
        mView = view;
        mFileFactory = fileFactory;
        mExoPlayer = exoPlayer;
    }

    @Override
    public void start() {

    }

    @Override
    public void clickTakePicture() {
        if (!mView.isActive()) return;

        Uri uri;

        try {
            java.io.File photoFile = mFileFactory.createFile();
            uri = mFileFactory.getUri(photoFile);
        } catch (IOException | NullPointerException e) {
            // todo: check why message isn't being displayed
            mView.showErrorMessage("Could not take picture. Please try again.");
            return;
        }

        mView.takePicture(uri, REQUEST_IMAGE_CAPTURE);
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

            MultimediaType multimediaType;

            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    multimediaType = MultimediaType.Photo;
                    break;
                case REQUEST_VIDEO_CAPTURE:
                    multimediaType = MultimediaType.Video;
                    break;
                case REQUEST_VOICE_CAPTURE:
                    multimediaType = MultimediaType.Voice;
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            showMultimedia(multimediaType, uri, actionHandler);
        } catch (NullPointerException | IllegalArgumentException | FileNotFoundException e) {
            mView.finish();
        }
    }

    @Override
    public void showMultimedia(
            MultimediaType multimediaType,
            Uri multimediaUri,
            BaseMultimediaViewActionHandler actionHandler) {

        BaseMultimediaPlayer player;

        switch (multimediaType) {
            case Photo:
                player = new PhotoMultimediaPlayer(actionHandler);
                break;
            case Video:
                player = new VideoMultimediaPlayer(
                        actionHandler,
                        mContext,
                        mExoPlayer,
                        multimediaUri);
                break;
            case Voice:
                player = new VoiceMultimediaPlayer(
                        actionHandler,
                        mContext,
                        mExoPlayer,
                        multimediaUri);
                break;
            default:
                throw new IllegalArgumentException();
        }

        mView.showLoadingMultimedia(false);
        mView.showMultimedia(multimediaUri, player);
    }

    @Override
    public void saveEvidence(
            String title,
            long achievementId,
            MultimediaType multimediaType,
            Uri multimediaUri) {

        if (!mView.isActive()) return;

        mView.hideKeyboard();

        BaseValidation validation = new Validator(mContext)
                .addProperty(
                        R.string.title,
                        title,
                        new StringLengthRule(5, 100))
                .addProperty(
                        R.string.achievement,
                        achievementId,
                        new ValidIdRule())
                .addProperty(
                        R.string.multimedia,
                        multimediaType,
                        new NotNullRule())
                .addProperty(
                        R.string.multimedia,
                        multimediaUri,
                        new NotNullRule())
                .validate();

        if (!validation.isValid()) {
            mView.showErrorMessage(validation.getFirstError());
            return;
        }

        Evidence evidence = new Evidence(title, achievementId, multimediaType, multimediaUri);

        mView.upload(evidence);
        mView.finish();
    }
}