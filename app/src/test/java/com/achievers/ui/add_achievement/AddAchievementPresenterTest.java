package com.achievers.ui.add_achievement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Involvement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.files.FilesDataSource;
import com.achievers.data.source.involvements.InvolvementsDataSource;
import com.achievers.utils.GeneratorUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.Is.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class AddAchievementPresenterTest {

    @Mock private Context mContext;
    @Mock private AddAchievementContract.View mView;

    @Mock private AchievementsDataSource mAchievementsDataSource;
    @Mock private FilesDataSource mFilesDataSource;
    @Mock private InvolvementsDataSource mInvolvementsDataSource;

    @Mock private Intent mIntent;

    @Captor private ArgumentCaptor<List<Involvement>> mActualLoadCaptor;

    private static final String sLoadFailure = "Could not load evidence";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private static final String sValidTitle = "achievement";
    private static final String sValidDescription = "description";
    private static final Uri sValidUri = mock(Uri.class);
    private static final Involvement sValidInvolvement = Involvement.Gold;
    private static final int sValidInvolvementPosition = 1;

    private AddAchievementContract.Presenter mPresenter;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        GeneratorUtils.initialize(new Random(), new Faker());

        mPresenter = new AddAchievementPresenter(
                mContext,
                mView,
                mInvolvementsDataSource);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(mView);

        verifyNoMoreInteractions(mAchievementsDataSource);
        verifyNoMoreInteractions(mFilesDataSource);
        verifyNoMoreInteractions(mInvolvementsDataSource);
    }

    @Test
    public void loadInvolvements_initiallyInactiveView() {
        // arrange
        arrangeInvolvements(true, false, true);

        // act
        mPresenter.loadInvolvements();

        // assert
        verify(mView).isActive();
    }

    @Test
    public void loadInvolvements_callbackInactiveView() {
        // arrange
        arrangeInvolvements(true, true, false);

        // act
        mPresenter.loadInvolvements();

        // assert
        verify(mInvolvementsDataSource).loadInvolvements(any(LoadCallback.class));

        verify(mView, times(2)).isActive();
    }

    @Test
    public void loadInvolvements_successful() {
        // arrange
        arrangeInvolvements(true, true, true);

        // act
        mPresenter.loadInvolvements();

        // assert
        verify(mInvolvementsDataSource).loadInvolvements(any(LoadCallback.class));

        verify(mView).showInvolvements(mActualLoadCaptor.capture());
        verify(mView, times(2)).isActive();

        List<Involvement> expected = Arrays.asList(Involvement.values());
        List<Involvement> actual = mActualLoadCaptor.getValue();
        assertEquals(expected, actual);
    }

    @Test
    public void loadInvolvements_failure() {
        // arrange
        arrangeInvolvements(false, true, true);

        // act
        mPresenter.loadInvolvements();

        // assert
        verify(mInvolvementsDataSource).loadInvolvements(any(LoadCallback.class));

        verify(mView).showErrorMessage(any(String.class));
        verify(mView, times(2)).isActive();
    }

    @Test
    public void clickTakePicture_inactiveView() {
        when(mView.isActive()).thenReturn(false);

        // act
        mPresenter.clickTakePicture();

        // assert
        verify(mView).isActive();
    }

    @Test
    public void clickChoosePicture_inactiveView() {
        when(mView.isActive()).thenReturn(false);

        // act
        mPresenter.clickChoosePicture();

        // assert
        verify(mView).isActive();
    }

    @Test
    public void clickChoosePicture_choosePicture() {
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.clickChoosePicture();

        // assert
        verify(mView).isActive();
        verify(mView).choosePicture(any(String.class), any(int.class));
    }

    @Test
    public void clickDiscardPicture_inactiveView() {
        when(mView.isActive()).thenReturn(false);

        // act
        mPresenter.clickDiscardPicture();

        // assert
        verify(mView).isActive();
    }

    @Test
    public void clickDiscardPicture_showPictureNull() {
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.clickDiscardPicture();

        // assert
        verify(mView).isActive();
        verify(mView).showPicture(null);
    }

    @Test
    public void deliverPicture_inactiveView() {
        when(mView.isActive()).thenReturn(false);

        // act
        mPresenter.deliverPicture(0, 0, null);

        // assert
        verify(mView).isActive();
    }

    @Test
    public void deliverPicture_requestNotOk() {
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.deliverPicture(0, Activity.RESULT_OK, null);

        // assert
        verify(mView).isActive();
        verify(mView).showErrorMessage(any(String.class));
    }

    @Test
    public void deliverPicture_resultNotOk() {
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.deliverPicture(REQUEST_IMAGE_CAPTURE, Activity.RESULT_CANCELED, null);

        // assert
        verify(mView).isActive();
        verify(mView).showErrorMessage(any(String.class));
    }

    @Test
    public void deliverPicture_imageCapture_nullData_showPicture() {
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.deliverPicture(REQUEST_IMAGE_CAPTURE, Activity.RESULT_OK, null);

        // assert
        verify(mView).isActive();
        verify(mView).showErrorMessage(any(String.class));
    }

    @Test
    public void deliverPicture_imageCapture_validData_showPicture() {
        when(mView.isActive()).thenReturn(true);

        Uri uri = mock(Uri.class);
        when(mIntent.getData()).thenReturn(uri);

        // act
        mPresenter.deliverPicture(REQUEST_IMAGE_CAPTURE, Activity.RESULT_OK, mIntent);

        // assert
        verify(mView).isActive();
        verify(mView).showPictureLoading(true);
        verify(mView).showPicture(uri);
    }

    @Test
    public void deliverPicture_imagePick_nullData_showPicture() {
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.deliverPicture(REQUEST_IMAGE_PICK, Activity.RESULT_OK, null);

        // assert
        verify(mView).isActive();
        verify(mView).showErrorMessage(any(String.class));
    }

    @Test
    public void deliverPicture_imagePick_validData_showPicture() {
        when(mView.isActive()).thenReturn(true);

        Uri uri = mock(Uri.class);
        when(mIntent.getData()).thenReturn(uri);

        // act
        mPresenter.deliverPicture(REQUEST_IMAGE_PICK, Activity.RESULT_OK, mIntent);

        // assert
        verify(mView).isActive();
        verify(mView).showPictureLoading(true);
        verify(mView).showPicture(uri);
    }

    @Test
    public void saveAchievement_inactiveView() {
        when(mView.isActive()).thenReturn(false);

        // act
        mPresenter.saveAchievement(null, null, null, null, -1);

        // assert
        verify(mView).isActive();
    }

    @Test
    public void saveAchievement_invalidTitle() {
        when(mView.isActive()).thenReturn(true);
        when(mContext.getString(any(int.class))).thenReturn("value");

        // act
        mPresenter.saveAchievement("", sValidDescription, sValidUri, sValidInvolvement, sValidInvolvementPosition);

        // assert
        verify(mView).isActive();
        verify(mView).hideKeyboard();
        verify(mView).showErrorMessage(any(String.class));
    }

    @Test
    public void saveAchievement_invalidDescription() {
        when(mView.isActive()).thenReturn(true);
        when(mContext.getString(any(int.class))).thenReturn("value");

        // act
        mPresenter.saveAchievement(sValidTitle, "", sValidUri, sValidInvolvement, sValidInvolvementPosition);

        // assert
        verify(mView).isActive();
        verify(mView).hideKeyboard();
        verify(mView).showErrorMessage(any(String.class));
    }

    @Test
    public void saveAchievement_invalidUri() {
        when(mView.isActive()).thenReturn(true);
        when(mContext.getString(any(int.class))).thenReturn("value");

        // act
        mPresenter.saveAchievement(sValidTitle, sValidDescription, null, sValidInvolvement, sValidInvolvementPosition);

        // assert
        verify(mView).isActive();
        verify(mView).hideKeyboard();
        verify(mView).showErrorMessage(any(String.class));
    }

    @Test
    public void saveAchievement_invalidInvolvement() {
        when(mView.isActive()).thenReturn(true);
        when(mContext.getString(any(int.class))).thenReturn("value");

        // act
        mPresenter.saveAchievement(sValidTitle, sValidDescription, sValidUri, null, sValidInvolvementPosition);

        // assert
        verify(mView).isActive();
        verify(mView).hideKeyboard();
        verify(mView).showErrorMessage(any(String.class));
    }

    @Test
    public void saveAchievement_finish() {
        when(mView.isActive()).thenReturn(true);
        when(mContext.getString(any(int.class))).thenReturn("value");

        // act
        mPresenter.saveAchievement(sValidTitle, sValidDescription, sValidUri, sValidInvolvement, sValidInvolvementPosition);

        // assert
        verify(mView).isActive();
        verify(mView).hideKeyboard();
        verify(mView).upload(any(Achievement.class));
        verify(mView).finish();
    }

    private void arrangeInvolvements(
            final boolean isSuccessful,
            final Boolean initiallyInactiveView,
            final Boolean callbackInactiveView) {

        mActualLoadCaptor = ArgumentCaptor.forClass(List.class);

        when(mView.isActive()).thenReturn(initiallyInactiveView);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                LoadCallback<Involvement> callback =
                        (LoadCallback<Involvement>) invocation.getArguments()[0];

                when(mView.isActive()).thenReturn(callbackInactiveView);

                if (isSuccessful) {
                    callback.onSuccess(Arrays.asList(Involvement.values()));
                    return null;
                }

                callback.onFailure(sLoadFailure);
                return null;
            }
        }).when(mInvolvementsDataSource).loadInvolvements(
                any(LoadCallback.class));
    }
}
