package com.achievers.ui.achievements;

import android.app.Activity;
import android.databinding.Observable;
import android.os.Build;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.R;
import com.achievers.ui._base._shadows.IntentShadow;
import com.achievers.ui.add_achievement.AddAchievementActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.robolectric.Shadows.shadowOf;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP,
        constants = BuildConfig.class,
        application = AchieversDebugTestApplication.class)
public class AchievementsFragmentTest {

    private @Mock AchievementsContracts.Presenter mPresenter;
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

        startFragment(mFragment, AchievementsActivity.class);

        verify(mViewModel).setAdapter(isA(AchievementsContracts.Adapter.class));
        verify(mViewModel).getPage();

        verify(mPresenter).start();
        verify(mPresenter).loadAchievements(0);
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
}