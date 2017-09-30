package com.achievers.ui.achievements;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.achievements.AchievementsMockDataSource;
import com.achievers.generator.AchievementsGenerator;
import com.achievers.utils.GeneratorUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class AchievementsPresenterTest {

    @Mock private Contracts.View mView;
    @Mock private AchievementsDataSource mDataSource;
    @Mock private LoadCallback<Achievement> mLoadCallback;

    @Captor private ArgumentCaptor<List<Achievement>> mExpectedLoad;
    @Captor private ArgumentCaptor<List<Achievement>> mActualLoad;

    private Contracts.Presenter mPresenter;

    @Before
    public void setupPresenter() {
        MockitoAnnotations.initMocks(this);
        GeneratorUtils.initialize(new Random(), new Faker());

        mDataSource = AchievementsMockDataSource.getInstance();
        mPresenter = new Presenter(mDataSource, mView);
    }

    @Test
    public void start() {
        // arrange
        arrangeLoad(0);

        // act
        mPresenter.start();

        // assert
        assertLoad();
    }

    @Test
    public void loadAchievements_firstPage() {
        // arrange
        int page = 0;
        arrangeLoad(page);

        // act
        mPresenter.loadAchievements(page);

        // assert
        assertLoad();
    }

    @Test
    public void loadAchievements_thirdPage() {
        // arrange
        int page = 3;
        arrangeLoad(page);

        // act
        mPresenter.loadAchievements(page);

        // assert
        assertLoad();
    }

    private void arrangeLoad(int page) {
        mExpectedLoad = ArgumentCaptor.forClass(List.class);
        mActualLoad = ArgumentCaptor.forClass(List.class);

        when(mView.isActive()).thenReturn(true);
        mDataSource.loadAchievements(page, mLoadCallback);
    }

    private void assertLoad() {
        verify(mView).setLoadingIndicator(true);
        verify(mView).showAchievements(mActualLoad.capture());
        verify(mView).setLoadingIndicator(false);
        verify(mView, times(2)).isActive();
        verifyNoMoreInteractions(mView);

        verify(mLoadCallback).onSuccess(mExpectedLoad.capture());

        assertEquals(mExpectedLoad.getValue().size(), mActualLoad.getValue().size());
        assertEquals(mExpectedLoad.getValue(), mActualLoad.getValue());
    }
}