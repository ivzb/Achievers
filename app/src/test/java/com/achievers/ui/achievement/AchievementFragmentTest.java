package com.achievers.ui.achievement;

import android.app.Activity;
import android.os.Build;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.data.entities.Achievement;
import com.achievers.ui._base.AbstractAdapter;
import com.achievers.ui._base._mocks.AchievementActivityMock;
import com.achievers.ui._base._mocks.AchievementsActivityMock;
import com.achievers.ui.achievements.AchievementsContract;
import com.achievers.ui.achievements.AchievementsFragment;
import com.achievers.ui.achievements.AchievementsViewModel;
import com.achievers.ui.add_achievement.AddAchievementActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.achievers.Config.RECYCLER_INITIAL_PAGE;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
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

        verify(mViewModel).setAdapter(isA(AbstractAdapter.class));
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
}
