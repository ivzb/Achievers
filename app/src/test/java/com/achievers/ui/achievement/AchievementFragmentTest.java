package com.achievers.ui.achievement;

import android.os.Build;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Evidence;
import com.achievers.ui._base.EndlessAdapterFragmentTest;
import com.achievers.ui._base._contracts.adapters.BaseMultimediaAdapter;
import com.achievers.ui._base._mocks.AchievementActivityMock;
import com.achievers.ui._base.adapters.ActionHandlerAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP,
        constants = BuildConfig.class,
        application = AchieversDebugTestApplication.class)
public class AchievementFragmentTest
        extends EndlessAdapterFragmentTest<Evidence, AchievementView, AchievementContract.Presenter, AchievementViewModel> {

    private @Mock AchievementContract.Presenter mPresenter;
    private @Mock AchievementViewModel mViewModel;
    private @Mock Achievement mAchievement;

    private AchievementView mFragment;

    private static final long sAchievementId = 5;

    public AchievementFragmentTest() {
        super(Evidence.class);
    }

    @Override
    public AchievementView getFragment() {
        return mFragment;
    }

    @Override
    public AchievementContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public AchievementViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public Evidence instantiateModel(Long id) {
        if (id == null) return new Evidence();
        return new Evidence(id);
    }

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        mFragment = new AchievementView();
        mFragment.setPresenter(mPresenter);
        mFragment.setViewModel(mViewModel);

        when(mAchievement.getId()).thenReturn(sAchievementId);
        when(mViewModel.getAchievement()).thenReturn(mAchievement);
        when(mViewModel.getContainerId()).thenReturn(sAchievementId);

        startFragment(mFragment, AchievementActivityMock.class);

        verify(mViewModel).setAdapter(isA(ActionHandlerAdapter.class));
        verify(mViewModel).getPage();
        verify(mViewModel).getContainerId();

        verify(mPresenter).start();
        verify(mPresenter).refresh(sAchievementId);
    }

    @Test
    @Override
    public void show() {
        // arrange
        List<Evidence> entities = new ArrayList<>();
        for (long i = 0; i < 5; i++) entities.add(instantiateModel(i));

        BaseMultimediaAdapter<Evidence> adapter = mock(BaseMultimediaAdapter.class);
        when(getViewModel().getAdapter()).thenReturn(adapter);

        // act
        getFragment().show(entities);

        // assert
        verify(getViewModel()).getAdapter();
        verify(adapter).add(eq(entities));
    }

    @Test
    public void onRefresh() {
        // act
        getFragment().onRefresh();

        // assert
        verify(mViewModel, times(2)).getContainerId();
        verify(getPresenter(), times(2)).refresh(eq(sAchievementId));
    }
}