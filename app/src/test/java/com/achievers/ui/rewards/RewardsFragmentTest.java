package com.achievers.ui.rewards;

import android.os.Build;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.data.entities.Reward;
import com.achievers.ui._base.AbstractAdapter;
import com.achievers.ui._base.EndlessAdapterFragmentTest;
import com.achievers.ui._base._mocks.QuestsActivityMock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP,
        constants = BuildConfig.class,
        application = AchieversDebugTestApplication.class)
public class RewardsFragmentTest
        extends EndlessAdapterFragmentTest<Reward, RewardsView, RewardsContract.Presenter, RewardsViewModel> {

    private @Mock RewardsContract.Presenter mPresenter;
    private @Mock RewardsViewModel mViewModel;

    private RewardsView mFragment;

    public RewardsFragmentTest() {
        super(Reward.class);
    }

    @Override
    public RewardsView getFragment() {
        return mFragment;
    }

    @Override
    public RewardsContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public RewardsViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public Reward instantiateModel(Long id) {
        if (id == null) return new Reward();
        return new Reward(id);
    }

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        mFragment = new RewardsView();
        mFragment.setPresenter(mPresenter);
        mFragment.setViewModel(mViewModel);

        startFragment(mFragment, QuestsActivityMock.class);

        verify(mViewModel).setAdapter(isA(AbstractAdapter.class));
        verify(mViewModel).getPage();

        verify(mPresenter).start();
        verify(mPresenter).refresh(isNull(Long.class));
    }

    @Test
    public void onActivityResult() {
        // act
        getFragment().onActivityResult(-1, -1, null);

        // assert
        verify(mPresenter).result(eq(-1), eq(-1));
    }

    @Test
    public void onRefresh() {
        // act
        mFragment.onRefresh();

        // assert
        verify(mPresenter, times(2)).refresh(isNull(Long.class));
    }
}