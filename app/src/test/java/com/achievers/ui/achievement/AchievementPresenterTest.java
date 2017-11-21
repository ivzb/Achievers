package com.achievers.ui.achievement;

import android.content.Context;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Evidence;
import com.achievers.data.source.evidences.EvidencesDataSource;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class AchievementPresenterTest {

    @Mock private Context mContext;
    @Mock private AchievementContract.View mView;
    @Mock private EvidencesDataSource mDataSource;

    @Captor private ArgumentCaptor<List<Evidence>> mLoadCaptor;

    private AchievementContract.Presenter mPresenter;

    private List<Evidence> mExpectedLoad;

    private long mAchievementId;

    private static final String sLoadFailure = "Could not load evidences";

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        GeneratorUtils.initialize(new Random(), new Faker());

        mPresenter = new AchievementPresenter(mContext, mView, mDataSource);
        mAchievementId = 5;
    }

    @After
    public void after() {
        verifyNoMoreInteractions(mContext);
        verifyNoMoreInteractions(mView);
        verifyNoMoreInteractions(mDataSource);
    }

    @Test(expected = NullPointerException.class)
    public void nullContext_shouldThrow() {
        new AchievementPresenter(null, mView, mDataSource);
    }

    @Test(expected = NullPointerException.class)
    public void nullView_shouldThrow() {
        new AchievementPresenter(mContext, null, mDataSource);
    }

    @Test(expected = NullPointerException.class)
    public void nullDataSource_shouldThrow() {
        new AchievementPresenter(mContext, mView, null);
    }

    @Test
    public void start_shouldDoNothing() {
        mPresenter.start();
        verifyNoMoreInteractions(mView);
    }

    @Test
    public void refresh() {
        // arrange
        int page = 0;

        arrangeLoad(
                true,
                true,
                true,
                page);

        // act
        mPresenter.refresh(mAchievementId);

        // assert
        assertSuccessfulLoad(mAchievementId, page);
    }

    @Test
    public void loadEvidences_firstPage_successful() {
        int page = 0;

        arrangeLoad(
                true,
                true,
                true,
                page);

        actLoad(mAchievementId, page);
        assertSuccessfulLoad(mAchievementId, page);
    }

    @Test
    public void loadEvidences_firstPage_failure() {
        int page = 0;

        arrangeLoad(
                false,
                true,
                true,
                page);

        actLoad(mAchievementId, page);
        assertFailureLoad(mAchievementId, page);
    }


    @Test
    public void loadEvidences_thirdPage_successful() {
        int page = 9;

        arrangeLoad(
                true,
                true,
                true,
                page);

        actLoad(mAchievementId, page);
        assertSuccessfulLoad(mAchievementId, page);
    }

    @Test
    public void loadEvidences_thirdPage_failure() {
        int page = 9;

        arrangeLoad(
                false,
                true,
                true,
                page);

        actLoad(mAchievementId, page);
        assertFailureLoad(mAchievementId, page);
    }

    @Test
    public void loadEvidences_initiallyInactiveView() {
        int page = 0;

        arrangeLoad(
                null,
                false,
                null,
                page);

        actLoad(mAchievementId, page);

        // assert
        verify(mView).isActive();
        verifyNoMoreInteractions(mView);
    }

    @Test
    public void loadEvidences_callbackInactiveView() {
        int page = 0;

        arrangeLoad(
                true,
                true,
                false,
                page);

        actLoad(mAchievementId, page);

        // assert
        verify(mView).setLoadingIndicator(true);
        verify(mDataSource).load(eq(mAchievementId), eq(page), any(LoadCallback.class));
        verify(mView, times(2)).isActive();
        verifyNoMoreInteractions(mView);
    }

    private void arrangeLoad(
            final Boolean isSuccessful,
            final Boolean initiallyInactiveView,
            final Boolean callbackInactiveView,
            final int page) {

        mLoadCaptor = ArgumentCaptor.forClass(List.class);

        when(mView.isActive()).thenReturn(initiallyInactiveView);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                LoadCallback<Evidence> callback =
                        (LoadCallback<Evidence>) invocation.getArguments()[2];

                when(mView.isActive()).thenReturn(callbackInactiveView);

                if (isSuccessful) {
                    mExpectedLoad = generate(page);
                    callback.onSuccess(mExpectedLoad, page);
                    return null;
                }

                callback.onFailure(sLoadFailure);
                return null;
            }
        }).when(mDataSource).load(
                any(Long.class), any(int.class), any(LoadCallback.class));
    }

    private void actLoad(long achievementId, int page) {
        mPresenter.load(achievementId, page);
    }

    private void assertSuccessfulLoad(long achievementId, int page) {
        verify(mView).setLoadingIndicator(true);

        verify(mDataSource).load(eq(achievementId), eq(page), any(LoadCallback.class));
        verify(mView).show(mLoadCaptor.capture());
        verify(mView).setLoadingIndicator(false);
        verify(mView).setPage(any(int.class));
        verify(mView, times(2)).isActive();
        verifyNoMoreInteractions(mView);

        List<Evidence> actualLoad = mLoadCaptor.getValue();
        assertTrue(mExpectedLoad == actualLoad);
    }

    private void assertFailureLoad(long achievementId, int page) {
        verify(mView).setLoadingIndicator(true);

        verify(mDataSource).load(eq(achievementId), eq(page), any(LoadCallback.class));
        verify(mView).showErrorMessage(any(String.class));
        verify(mView).setLoadingIndicator(false);
        verify(mView, times(2)).isActive();
        verifyNoMoreInteractions(mView);
    }

    private List<Evidence> generate(int page) {
        List<Evidence> evidences = new ArrayList<>();
        int end = 9 * page;

        for (int id = 0; id < end; id++) {
            evidences.add(new Evidence(id));
        }

        return evidences;
    }
}