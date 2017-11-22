package com.achievers.ui.quest;

import android.os.Build;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Quest;
import com.achievers.ui._base.EndlessAdapterFragmentTest;
import com.achievers.ui._base._contracts.adapters.BaseAdapter;
import com.achievers.ui._base._contracts.adapters.BaseMultimediaAdapter;
import com.achievers.ui._base._mocks.AchievementActivityMock;

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
public class QuestFragmentTest
        extends EndlessAdapterFragmentTest<Achievement, QuestView, QuestContract.Presenter, QuestViewModel> {

    private @Mock QuestContract.Presenter mPresenter;
    private @Mock QuestViewModel mViewModel;
    private @Mock Quest mQuest;

    private QuestView mFragment;

    private static final long sQuestId = 5;

    public QuestFragmentTest() {
        super(Achievement.class);
    }

    @Override
    public QuestView getFragment() {
        return mFragment;
    }

    @Override
    public QuestContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public QuestViewModel getViewModel() {
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

        mFragment = new QuestView();
        mFragment.setPresenter(mPresenter);
        mFragment.setViewModel(mViewModel);

        when(mQuest.getId()).thenReturn(sQuestId);
        when(mViewModel.getQuest()).thenReturn(mQuest);

        startFragment(mFragment, AchievementActivityMock.class);

        verify(mViewModel).setAdapter(isA(BaseAdapter.class));
        verify(mViewModel).getPage();
        verify(mViewModel).getQuest();

        verify(mPresenter).start();
        verify(mPresenter).refresh(sQuestId);
    }

    @Test
    @Override
    public void show() {
        // arrange
        List<Achievement> entities = new ArrayList<>();
        for (long i = 0; i < 5; i++) entities.add(instantiateModel(i));

        BaseMultimediaAdapter<Achievement> adapter = mock(BaseMultimediaAdapter.class);
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
        verify(mViewModel, times(2)).getQuest();
        verify(getPresenter(), times(2)).refresh(eq(sQuestId));
    }
}