package com.achievers.ui.achievements;

import android.content.Context;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.utils.GeneratorUtils;

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
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class AchievementsPresenterTest {

    @Mock private Context mContext;
    @Mock private AchievementsContracts.View mView;
    @Mock private AchievementsDataSource mDataSource;

    @Captor private ArgumentCaptor<List<Achievement>> mActualLoadCaptor;

    private AchievementsContracts.Presenter mPresenter;

    private List<Achievement> mExpectedLoad;

    private static final String sLoadFailure = "Could not load achievements";

    @Before
    public void setupPresenter() {
        MockitoAnnotations.initMocks(this);
        GeneratorUtils.initialize(new Random(), new Faker());

        mPresenter = new AchievementsPresenter(mContext, mView, mDataSource);
    }

    @Test
    public void start_shouldInternallyCallLoadAchievements() {
        int page = 0;

        arrangeLoad(
                true,
                true,
                true,
                page);

        mPresenter.start();

        assertSuccessfulLoad();
    }

    @Test
    public void loadAchievements_firstPage_successful() {
        int page = 0;

        arrangeLoad(
                true,
                true,
                true,
                page);

        actLoad(page);
        assertSuccessfulLoad();
    }

    @Test
    public void loadAchievements_firstPage_failure() {
        int page = 0;

        arrangeLoad(
                false,
                true,
                true,
                page);

        actLoad(page);
        assertFailureLoad();
    }

    @Test
    public void loadAchievements_thirdPage_successful() {
        int page = 9;

        arrangeLoad(
                true,
                true,
                true,
                page);

        actLoad(page);
        assertSuccessfulLoad();
    }

    @Test
    public void loadAchievements_thirdPage_failure() {
        int page = 9;

        arrangeLoad(
                false,
                true,
                true,
                page);

        actLoad(page);
        assertFailureLoad();
    }

    @Test
    public void loadAchievements_initiallyInactiveView() {
        int page = 0;

        arrangeLoad(
                null,
                false,
                null,
                page);

        actLoad(page);

        // assert
        verify(mView).isActive();
        verifyNoMoreInteractions(mView);
    }

    @Test
    public void loadAchievements_callbackInactiveView() {
        int page = 0;

        arrangeLoad(
                true,
                true,
                false,
                page);

        actLoad(page);

        // assert
        verify(mView).setLoadingIndicator(true);
        verify(mView, times(2)).isActive();
        verifyNoMoreInteractions(mView);
    }

    @Test
    public void clickAchievement_shouldOpenAchievementUi() {
        // arrange
        when(mView.isActive()).thenReturn(true);
        Achievement achievement = new Achievement();

        // act
        mPresenter.clickAchievement(achievement);

        // assert
        verify(mView).isActive();
        verify(mView).openAchievementUi(achievement);
        verifyNoMoreInteractions(mView);
    }

    @Test
    public void clickAchievement_withoutAchievement_shouldShowErrorMessage() {
        // arrange
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.clickAchievement(null);

        // assert
        verify(mView).isActive();
        verify(mView).showErrorMessage(any(String.class));
        verifyNoMoreInteractions(mView);
    }

    @Test
    public void clickAchievement_inactiveView_shouldReturn() {
        // arrange
        when(mView.isActive()).thenReturn(false);

        // act
        mPresenter.clickAchievement(null);

        // assert
        verify(mView).isActive();
        verifyNoMoreInteractions(mView);
    }

    @Test
    public void clickAddAchievement_shouldOpenAddAchievementUi() {
        // arrange
        when(mView.isActive()).thenReturn(true);

        // act
        mPresenter.clickAddAchievement();

        // assert
        verify(mView).isActive();
        verify(mView).openAddAchievementUi();
        verifyNoMoreInteractions(mView);
    }

    @Test
    public void clickAddAchievement_inactiveView_shouldReturn() {
        // arrange
        when(mView.isActive()).thenReturn(false);

        // act
        mPresenter.clickAddAchievement();

        // assert
        verify(mView).isActive();
        verifyNoMoreInteractions(mView);
    }

    private void arrangeLoad(
            final Boolean isSuccessful,
            final Boolean initiallyInactiveView,
            final Boolean callbackInactiveView,
            final int page) {

        mActualLoadCaptor = ArgumentCaptor.forClass(List.class);

        when(mView.isActive()).thenReturn(initiallyInactiveView);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                LoadCallback<Achievement> callback =
                        (LoadCallback<Achievement>) invocation.getArguments()[1];

                when(mView.isActive()).thenReturn(callbackInactiveView);

                if (isSuccessful) {
                    mExpectedLoad = generate(page);
                    callback.onSuccess(mExpectedLoad);
                    return null;
                }

                callback.onFailure(sLoadFailure);
                return null;
            }
        }).when(mDataSource).loadAchievements(
                any(int.class), any(LoadCallback.class));
    }

    private void actLoad(int page) {
        mPresenter.loadAchievements(page);
    }

    private void assertSuccessfulLoad() {
        verify(mView).setLoadingIndicator(true);
        verify(mView).showAchievements(mActualLoadCaptor.capture());
        verify(mView).setLoadingIndicator(false);
        verify(mView, times(2)).isActive();
        verifyNoMoreInteractions(mView);

        List<Achievement> actualLoad = mActualLoadCaptor.getValue();
        assertTrue(mExpectedLoad == actualLoad);
    }

    private void assertFailureLoad() {
        verify(mView).setLoadingIndicator(true);
        verify(mView).showErrorMessage(any(String.class));
        verify(mView).setLoadingIndicator(false);
        verify(mView, times(2)).isActive();
        verifyNoMoreInteractions(mView);
    }

    private List<Achievement> generate(int page) {
        List<Achievement> achievements = new ArrayList<>();
        int end = 9 * page;

        for (int id = 0; id < end; id++) {
            achievements.add(new Achievement(id));
        }

        return achievements;
    }
}