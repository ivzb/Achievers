package com.achievers.ui.achievements;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.data.entities.Achievement;
import com.achievers.ui._base.EndlessAdapterFragmentTest;
import com.achievers.ui._base._mocks.AchievementsActivityMock;
import com.achievers.ui._base.adapters.ActionHandlerAdapter;
import com.achievers.ui.achievement.AchievementActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.parceler.Parcels;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP,
        constants = BuildConfig.class,
        application = AchieversDebugTestApplication.class)
public class AchievementsFragmentTest
        extends EndlessAdapterFragmentTest<Achievement, AchievementsView, AchievementsContract.Presenter, AchievementsViewModel> {

    private @Mock AchievementsContract.Presenter mPresenter;
    private @Mock AchievementsViewModel mViewModel;

    private AchievementsView mFragment;

    public AchievementsFragmentTest() {
        super(Achievement.class);
    }

    @Override
    public AchievementsView getFragment() {
        return mFragment;
    }

    @Override
    public AchievementsContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public AchievementsViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public Achievement instantiateModel(Long id) {
        if (id == null) return new Achievement();
        return new Achievement(id);
    }

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        mFragment = new AchievementsView();
        mFragment.setPresenter(mPresenter);
        mFragment.setViewModel(mViewModel);

        when(mViewModel.getContainerId()).thenReturn(null);

        startFragment(mFragment, AchievementsActivityMock.class);

        verify(mViewModel).setAdapter(isA(ActionHandlerAdapter.class));
        verify(mViewModel).getPage();
        verify(mViewModel).getContainerId();

        verify(mPresenter).start();
        verify(mPresenter).refresh(isNull(Long.class));
    }

    @Test
    public void openAchievementUi() {
        // arrange
        Achievement model = instantiateModel(503L);

        // act
        getFragment().openUi(model);

        // assert
        Intent intent = ShadowApplication.getInstance().getNextStartedActivity();

        Bundle extras = intent.getExtras();
        assertNotNull(extras);
        assertTrue(extras.containsKey(AchievementActivity.EXTRA_ACHIEVEMENT));
        Achievement actual = Parcels.unwrap(extras.getParcelable(AchievementActivity.EXTRA_ACHIEVEMENT));
        assertEquals(model, actual);
    }

    @Test
    public void onAchievementClick() {
        // arrange
        Achievement model = mock(Achievement.class);

        // act
        getFragment().onAdapterEntityClick(model);

        // assert
        verify(getPresenter()).click(eq(model));
    }
}