package com.achievers.ui.achievement;

import android.os.Build;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Evidence;
import com.achievers.ui._base._mocks.AchievementActivityMock;
import com.achievers.ui._base.adapters.MultimediaAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP,
        constants = BuildConfig.class,
        application = AchieversDebugTestApplication.class)
public class AchievementFragmentTest {

    private @Mock AchievementContract.Presenter mPresenter;
    private @Mock AchievementViewModel mViewModel;
    private @Mock Achievement mAchievement;

    private AchievementFragment mFragment;

    private static final long sAchievementId = 5;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        mFragment = new AchievementFragment();
        mFragment.setPresenter(mPresenter);
        mFragment.setViewModel(mViewModel);

        when(mAchievement.getId()).thenReturn(sAchievementId);
        when(mViewModel.getAchievement()).thenReturn(mAchievement);

        startFragment(mFragment, AchievementActivityMock.class);

        verify(mViewModel).setAdapter(isA(MultimediaAdapter.class));
        verify(mViewModel).getPage();
        verify(mViewModel).getAchievement();

        verify(mPresenter).start();
        verify(mPresenter).refresh(sAchievementId);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(mViewModel);
        verifyNoMoreInteractions(mPresenter);
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(mFragment);
    }

    @Test
    public void showEvidences() {
        // arrange
        List<Evidence> evidences = new ArrayList<>();
        for (int i = 0; i < 5; i++) evidences.add(new Evidence(i));

        MultimediaAdapter<Evidence> adapter = mock(MultimediaAdapter.class);
        when(mViewModel.getAdapter()).thenReturn(adapter);

        // act
        mFragment.showEvidences(evidences);

        // assert
        verify(mViewModel).getAdapter();
        verify(adapter).add(eq(evidences));
    }

    @Test
    public void getPage() {
        // act
        mFragment.getPage();

        // assert
        verify(mViewModel, times(2)).getPage();
    }

    @Test
    public void setPage() {
        // arrange
        int page = 5;

        // act
        mFragment.setPage(page);

        // assert
        verify(mViewModel).setPage(eq(5));
    }
}
