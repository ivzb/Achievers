package com.achievers.ui.constributions;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.AchievementProgress;
import com.achievers.ui._base.AbstractAdapter;
import com.achievers.ui._base.EndlessAdapterFragmentTest;
import com.achievers.ui._base._mocks.AchievementsActivityMock;
import com.achievers.ui._base._shadows.ResourcesCompatShadow;
import com.achievers.ui.achievement.AchievementActivity;
import com.achievers.ui.contributions.ContributionsContract;
import com.achievers.ui.contributions.ContributionsView;
import com.achievers.ui.contributions.ContributionsViewModel;

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
        shadows = { ResourcesCompatShadow.class },
        application = AchieversDebugTestApplication.class)
public class ContributionsFragmentTest
        extends EndlessAdapterFragmentTest<AchievementProgress, ContributionsView, ContributionsContract.Presenter, ContributionsViewModel> {

    private @Mock ContributionsContract.Presenter mPresenter;
    private @Mock ContributionsViewModel mViewModel;

    private ContributionsView mFragment;

    public ContributionsFragmentTest() {
        super(AchievementProgress.class);
    }

    @Override
    public ContributionsView getFragment() {
        return mFragment;
    }

    @Override
    public ContributionsContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public ContributionsViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public AchievementProgress instantiateModel(Long id) {
        if (id == null) return new AchievementProgress();
        return new AchievementProgress(id);
    }

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        mFragment = new ContributionsView();
        mFragment.setPresenter(mPresenter);
        mFragment.setViewModel(mViewModel);

        when(mViewModel.getContainerId()).thenReturn(null);

        startFragment(mFragment, AchievementsActivityMock.class);

        verify(mViewModel).setAdapter(isA(AbstractAdapter.class));
        verify(mViewModel).getPage();
        verify(mViewModel).getContainerId();

        verify(mPresenter).start();
        verify(mPresenter).refresh(isNull(Long.class));
    }

    @Test
    public void openAchievementUi() {
        // arrange
        AchievementProgress model = instantiateModel(503L);

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
        AchievementProgress model = mock(AchievementProgress.class);

        // act
        getFragment().onAdapterEntityClick(model);

        // assert
        verify(getPresenter()).click(eq(model));
    }
}