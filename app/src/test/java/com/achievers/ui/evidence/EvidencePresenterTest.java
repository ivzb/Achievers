package com.achievers.ui.evidence;

import android.Manifest;

import com.achievers.data.source.evidences.EvidencesDataSource;
import com.achievers.utils.ui.multimedia.MultimediaType;
import com.achievers.utils.ui.multimedia.MultimediaView;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaBuilder;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static android.support.v4.content.PermissionChecker.PERMISSION_DENIED;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static com.achievers.ui.evidence.EvidencePresenter.REQUEST_READ_EXTERNAL_STORAGE_PERMISSION;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class EvidencePresenterTest {

    @Mock private EvidenceContract.View mView;
    @Mock private EvidencesDataSource mDataSource;

    @Captor
    private ArgumentCaptor<String[]> mPermissionsCaptor;

    private EvidenceContract.Presenter mPresenter;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new EvidencePresenter(
                mView,
                mDataSource);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(mView);
        verifyNoMoreInteractions(mDataSource);
    }

    @Test
    public void requestReadExternalStoragePermission() {
        // arrange
        String[] expectedPermissions = { Manifest.permission.READ_EXTERNAL_STORAGE };

        // act
        mPresenter.requestReadExternalStoragePermission();

        // assert
        verify(mView).requestPermissions(mPermissionsCaptor.capture(), eq(REQUEST_READ_EXTERNAL_STORAGE_PERMISSION));

        String[] actualPermissions = mPermissionsCaptor.getValue();

        assertTrue(actualPermissions.length == 1);
        assertEquals(expectedPermissions[0], actualPermissions[0]);
    }

    @Test
    public void deliverPermissionsResult_recordAudio_permissionGranted() {
        // arrange
        int requestCode = REQUEST_READ_EXTERNAL_STORAGE_PERMISSION;
        int[] grantResults = new int[] { PERMISSION_GRANTED };

        // act
        mPresenter.deliverPermissionsResult(requestCode, grantResults);

        // assert
        verify(mView).initMultimedia();
    }

    @Test
    public void deliverPermissionsResult_recordAudio_permissionDenied() {
        // arrange
        int requestCode = REQUEST_READ_EXTERNAL_STORAGE_PERMISSION;
        int[] grantResults = new int[] { PERMISSION_DENIED };

        // act
        mPresenter.deliverPermissionsResult(requestCode, grantResults);

        // assert
        verify(mView).finish();
    }

    @Test
    public void deliverPermissionsResult_notRecordAudio_permissionGranted() {
        // arrange
        int requestCode = -1;
        int[] grantResults = new int[] { PERMISSION_GRANTED };

        // act
        mPresenter.deliverPermissionsResult(requestCode, grantResults);

        // assert
        verify(mView).finish();
    }

    @Test
    public void buildMultimedia() {
        // arrange
        MultimediaView view = mock(MultimediaView.class);
        MultimediaType type = mock(MultimediaType.class);
        String previewUrl = "preview url";
        BaseMultimediaPlayer player = mock(BaseMultimediaPlayer.class);

        BaseMultimediaBuilder multimediaBuilder = mock(BaseMultimediaBuilder.class);

        when(view.builder(any(MultimediaType.class))).thenReturn(multimediaBuilder);
        when(multimediaBuilder.withUrl(anyString())).thenReturn(multimediaBuilder);
        when(multimediaBuilder.withPlayer(any(BaseMultimediaPlayer.class))).thenReturn(multimediaBuilder);

        // act
        mPresenter.buildMultimedia(view, type, previewUrl, player);

        // assert
        verify(view).builder(eq(type));
        verify(multimediaBuilder).withUrl(eq(previewUrl));
        verify(multimediaBuilder).withPlayer(player);
        verify(multimediaBuilder).build();
    }

    @Test
    public void buildMultimedia_throw() {
        // arrange
        MultimediaView view = mock(MultimediaView.class);
        MultimediaType type = mock(MultimediaType.class);
        String previewUrl = "preview url";
        BaseMultimediaPlayer player = mock(BaseMultimediaPlayer.class);

        when(view.builder(any(MultimediaType.class))).thenThrow(NullPointerException.class);

        // act
        mPresenter.buildMultimedia(view, type, previewUrl, player);

        // assert
        verify(view).builder(eq(type));
        verify(mView).showMultimediaError();
    }
}