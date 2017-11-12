package com.achievers.ui.achievements;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.data.entities.Achievement;
import com.achievers.ui._base.AbstractAdapter;
import com.achievers.ui._base._mocks.AchievementsActivityMock;
import com.achievers.ui.achievement.AchievementActivity;
import com.achievers.ui.add_achievement.AddAchievementActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.parceler.Parcels;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
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
public class AchievementsFragmentTest {

    private @Mock AchievementsContract.Presenter mPresenter;
    private @Mock AchievementsViewModel mViewModel;

    private AchievementsFragment mFragment;

    private static final int sValidRequestCode = AddAchievementActivity.REQUEST_ADD_ACHIEVEMENT;
    private static final int sValidResultCode = Activity.RESULT_OK;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        mFragment = new AchievementsFragment();
        mFragment.setPresenter(mPresenter);
        mFragment.setViewModel(mViewModel);

        startFragment(mFragment, AchievementsActivityMock.class);

        verify(mViewModel).setAdapter(isA(AbstractAdapter.class));
        verify(mViewModel).getPage();

        verify(mPresenter).start();
        verify(mPresenter).refresh();
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
    public void onActivityResult() {
        // act
        mFragment.onActivityResult(sValidRequestCode, sValidResultCode, null);

        // assert
        verify(mPresenter).result(eq(sValidRequestCode), eq(sValidResultCode));
    }

    @Test
    public void showAchievements() {
        // arrange
        List<Achievement> achievements = new ArrayList<>();
        for (int i = 0; i < 5; i++) achievements.add(new Achievement(i));

        AbstractAdapter<Achievement> adapter = mock(AbstractAdapter.class);
        when(mViewModel.getAdapter()).thenReturn(adapter);

        // act
        mFragment.showAchievements(achievements);

        // assert
        verify(mViewModel).getAdapter();
        verify(adapter).add(eq(achievements));
    }

    @Test
    public void openAchievementUi() {
        // arrange
        Achievement achievement = new Achievement(503);

        // act
        mFragment.openAchievementUi(achievement);

        // assert
        Intent intent = ShadowApplication.getInstance().getNextStartedActivity();

        Bundle extras = intent.getExtras();
        assertNotNull(extras);
        assertTrue(extras.containsKey(AchievementActivity.EXTRA_ACHIEVEMENT));
        Achievement actual = Parcels.unwrap(extras.getParcelable(AchievementActivity.EXTRA_ACHIEVEMENT));
        assertEquals(achievement, actual);
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

    @Test
    public void onAchievementClick() {
        // arrange
        Achievement achievement = mock(Achievement.class);

        // act
        mFragment.onAdapterEntityClick(achievement);

        // assert
        verify(mPresenter).clickAchievement(eq(achievement));
    }

    @Test
    public void onRefresh() {
        // act
        mFragment.onRefresh();

        // assert
        verify(mPresenter, times(2)).refresh();
    }
}