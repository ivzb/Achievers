package com.achievers.ui.achievements;

import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsDataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class AchievementsPresenterTest {

    @Mock
    private AchievementsDataSource mDataSource;

    @Mock
    private Contracts.View mView;

    private Contracts.Presenter mPresenter;

    @Before
    public void setupPresenter() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new Presenter(mDataSource, mView);
    }

    @Test
    public void testStart() {
        when(mView.isActive()).thenReturn(true);

        mPresenter.start();

        // todo: mock mDataSource.loadAchievements
        ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);

        verify(mView).setLoadingIndicator(true);

        verify(mView).showAchievements(argument.capture());
        assertEquals(9, argument.getValue().size());

        verify(mView).setLoadingIndicator(false);

        verifyNoMoreInteractions(mView);
    }
}