package com.achievers.ui.add_evidence;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.achievers.data.entities.Evidence;
import com.achievers.utils.GeneratorUtils;
import com.achievers.utils.files.factory.FileFactory;
import com.achievers.utils.ui.multimedia.MultimediaType;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewActionHandler;
import com.achievers.utils.ui.multimedia.players.PhotoMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.VideoMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.VoiceMultimediaPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import io.bloco.faker.Faker;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.achievers.ui.add_evidence.AddEvidencePresenter.REQUEST_PHOTO_CAPTURE;
import static com.achievers.ui.add_evidence.AddEvidencePresenter.REQUEST_VIDEO_CAPTURE;
import static com.achievers.ui.add_evidence.AddEvidencePresenter.REQUEST_VOICE_CAPTURE;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class AddEvidencePresenterTest {

    @Mock private Context mContext;
    @Mock private AddEvidenceContract.View mView;
    @Mock private FileFactory mFileFactory;
    @Mock private SimpleExoPlayer mSimpleExoPlayer;

    @Mock private File mFile;
    @Mock private Intent mIntent;
    @Mock private BaseMultimediaViewActionHandler mActionHandler;

    private String mTitle;
    private long mAchievementId;
    @Mock private MultimediaType mMultimediaType;
    @Mock private Uri mUri;

    private AddEvidenceContract.Presenter mPresenter;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        GeneratorUtils.initialize(new Random(), new Faker());

        mPresenter = new AddEvidencePresenter(
                mContext,
                mView,
                mFileFactory,
                mSimpleExoPlayer);

        mTitle = "title";
        mAchievementId = 5;
    }

    @After
    public void after() {
        verifyNoMoreInteractions(mView);
        verifyNoMoreInteractions(mSimpleExoPlayer);
    }

    @Test(expected = NullPointerException.class)
    public void noContext_shouldThrow() {
        new AddEvidencePresenter(null, mView, mFileFactory, mSimpleExoPlayer);
    }

    @Test(expected = NullPointerException.class)
    public void noView_shouldThrow() {
        new AddEvidencePresenter(mContext, null, mFileFactory, mSimpleExoPlayer);
    }

    @Test(expected = NullPointerException.class)
    public void noFileFactory_shouldThrow() {
        new AddEvidencePresenter(mContext, mView, null, mSimpleExoPlayer);
    }

    @Test(expected = NullPointerException.class)
    public void noExoPlayer_shouldThrow() {
        new AddEvidencePresenter(mContext, mView, mFileFactory, null);
    }

    @Test
    public void clickTakePicture_inactiveView() {
        // arrange
        when(mView.isActive()).thenReturn(false);

        // act
        mPresenter.clickTakePicture();

        // assert
        verify(mView).isActive();
    }

    @Test
    public void clickTakePicture_throwNull() throws IOException {
        // arrange
        when(mView.isActive()).thenReturn(true);
        when(mFileFactory.createFile()).thenThrow(IOException.class);

        // act
        mPresenter.clickTakePicture();

        // assert
        verify(mView).isActive();
        verify(mView).showErrorMessage(anyString());
    }

    @Test
    public void clickTakePicture_nullFile() throws IOException {
        when(mView.isActive()).thenReturn(true);
        when(mFileFactory.createFile()).thenReturn(null);
        when(mFileFactory.getUri(isNull(File.class))).thenThrow(NullPointerException.class);

        // act
        mPresenter.clickTakePicture();

        // assert
        verify(mView).isActive();
        verify(mFileFactory).createFile();
        verify(mView).showErrorMessage(anyString());
    }

    @Test
    public void clickTakePicture_throwUri() throws IOException {
        when(mView.isActive()).thenReturn(true);
        when(mFileFactory.createFile()).thenReturn(mFile);
        when(mFileFactory.getUri(isA(File.class))).thenThrow(IOException.class);

        // act
        mPresenter.clickTakePicture();

        // assert
        verify(mView).isActive();
        verify(mFileFactory).createFile();
        verify(mView).showErrorMessage(anyString());
    }

    @Test
    public void clickTakePicture_takesPicture() throws IOException {
        when(mView.isActive()).thenReturn(true);
        when(mFileFactory.createFile()).thenReturn(mFile);
        when(mFileFactory.getUri(isA(File.class))).thenReturn(mUri);

        // act
        mPresenter.clickTakePicture();

        // assert
        verify(mView).isActive();
        verify(mFileFactory).createFile();
        verify(mFileFactory).getUri(isA(File.class));
        verify(mView).takePicture(eq(mUri), eq(REQUEST_PHOTO_CAPTURE));
    }

    @Test
    public void clickTakeVideo_inactiveView() {
        // arrange
        when(mView.isActive()).thenReturn(false);

        // act
        mPresenter.clickTakeVideo();

        // assert
        verify(mView).isActive();
    }

    @Test
    public void clickTakeVideo_takesVideo() {
        // arrange
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.clickTakeVideo();

        // assert
        verify(mView).isActive();
        verify(mView).takeVideo(eq(REQUEST_VIDEO_CAPTURE));
    }

    @Test
    public void clickRecordVoice_inactiveView() {
        // arrange
        when(mView.isActive()).thenReturn(false);

        // act
        mPresenter.clickRecordVoice();

        // assert
        verify(mView).isActive();
    }

    @Test
    public void clickRecordVoice_takesVideo() {
        // arrange
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.clickRecordVoice();

        // assert
        verify(mView).isActive();
        verify(mView).recordVoice(eq(REQUEST_VOICE_CAPTURE));
    }

    @Test
    public void deliverMultimedia_inactiveView() {
        // arrange
        when(mView.isActive()).thenReturn(false);

        // act
        mPresenter.deliverMultimedia(REQUEST_PHOTO_CAPTURE, RESULT_OK, mIntent, mActionHandler);

        // assert
        verify(mView).isActive();
    }

    @Test
    public void deliverMultimedia_canceledRequestCode() {
        // arrange
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.deliverMultimedia(REQUEST_PHOTO_CAPTURE, RESULT_CANCELED, mIntent, mActionHandler);

        // assert
        verify(mView).isActive();
        verify(mView).finish();
    }

    @Test
    public void deliverMultimedia_nullUri() {
        // arrange
        when(mView.isActive()).thenReturn(true);
        when(mIntent.getData()).thenReturn(null);

        // act
        mPresenter.deliverMultimedia(REQUEST_PHOTO_CAPTURE, RESULT_OK, mIntent, mActionHandler);

        // assert
        verify(mView).isActive();
        verify(mView).finish();
    }

    @Test
    public void deliverMultimedia_invalidRequestCode() {
        // arrange
        when(mView.isActive()).thenReturn(true);
        when(mIntent.getData()).thenReturn(mUri);

        // act
        mPresenter.deliverMultimedia(-1, RESULT_OK, mIntent, mActionHandler);

        // assert
        verify(mView).isActive();
        verify(mView).finish();
    }

    @Test
    public void deliverMultimedia_photoRequestCode() {
        deliverMultimedia(REQUEST_PHOTO_CAPTURE, PhotoMultimediaPlayer.class);
    }

    @Test
    public void deliverMultimedia_videoRequestCode() {
        deliverMultimedia(REQUEST_VIDEO_CAPTURE, VideoMultimediaPlayer.class);
    }

    @Test
    public void deliverMultimedia_voiceRequestCode() {
        deliverMultimedia(REQUEST_VOICE_CAPTURE, VoiceMultimediaPlayer.class);
    }

    private void deliverMultimedia(int requestCode, Class<? extends BaseMultimediaPlayer> player) {
        // arrange
        when(mView.isActive()).thenReturn(true);
        when(mIntent.getData()).thenReturn(mUri);

        // act
        mPresenter.deliverMultimedia(requestCode, RESULT_OK, mIntent, mActionHandler);

        // assert
        verify(mView).isActive();
        verify(mView).showLoadingMultimedia(eq(false));
        verify(mView).showMultimedia(
                eq(mUri),
                isA(player));
    }

    @Test
    public void saveEvidence_inactive() {
        // arrange
        when(mView.isActive()).thenReturn(false);

        // act
        mPresenter.saveEvidence(mTitle, mAchievementId, mMultimediaType, mUri);

        // assert
        verify(mView).isActive();
    }

    @Test
    public void saveEvidence_invalidTitle() {
        // arrange
        when(mContext.getString(anyInt())).thenReturn("property");
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.saveEvidence("", mAchievementId, mMultimediaType, mUri);

        // assert
        verify(mView).isActive();
        verify(mView).hideKeyboard();
        verify(mView).showErrorMessage(anyString());
    }

    @Test
    public void saveEvidence_invalidAchievementId() {
        // arrange
        when(mContext.getString(anyInt())).thenReturn("property");
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.saveEvidence(mTitle, -1, mMultimediaType, mUri);

        // assert
        verify(mView).isActive();
        verify(mView).hideKeyboard();
        verify(mView).showErrorMessage(anyString());
    }

    @Test
    public void saveEvidence_invalidMultimediaType() {
        // arrange
        when(mContext.getString(anyInt())).thenReturn("property");
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.saveEvidence(mTitle, mAchievementId, null, mUri);

        // assert
        verify(mView).isActive();
        verify(mView).hideKeyboard();
        verify(mView).showErrorMessage(anyString());
    }

    @Test
    public void saveEvidence_invalidUri() {
        // arrange
        when(mContext.getString(anyInt())).thenReturn("property");
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.saveEvidence(mTitle, mAchievementId, mMultimediaType, null);

        // assert
        verify(mView).isActive();
        verify(mView).hideKeyboard();
        verify(mView).showErrorMessage(anyString());
    }

    @Test
    public void saveEvidence_upload() {
        // arrange
        when(mContext.getString(anyInt())).thenReturn("property");
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.saveEvidence(mTitle, mAchievementId, mMultimediaType, mUri);

        // assert
        verify(mView).isActive();
        verify(mView).hideKeyboard();
        verify(mView).upload(isA(Evidence.class));
        verify(mView).finish();
    }
}