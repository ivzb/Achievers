package com.achievers.ui.quests;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.data.entities.Quest;
import com.achievers.ui._base.AbstractAdapter;
import com.achievers.ui._base.EndlessAdapterFragmentTest;
import com.achievers.ui._base._mocks.QuestsActivityMock;
import com.achievers.ui.quest.QuestActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP,
        constants = BuildConfig.class,
        application = AchieversDebugTestApplication.class)
public class QuestsFragmentTest
        extends EndlessAdapterFragmentTest<Quest, QuestsView, QuestsContract.Presenter, QuestsViewModel> {

    private @Mock QuestsContract.Presenter mPresenter;
    private @Mock QuestsViewModel mViewModel;

    private QuestsView mFragment;

    public QuestsFragmentTest() {
        super(Quest.class);
    }

    @Override
    public QuestsView getFragment() {
        return mFragment;
    }

    @Override
    public QuestsContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public QuestsViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public Quest instantiateModel(Long id) {
        if (id == null) return new Quest();
        return new Quest(id);
    }

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        mFragment = new QuestsView();
        mFragment.setPresenter(mPresenter);
        mFragment.setViewModel(mViewModel);

        when(mViewModel.getContainerId()).thenReturn(null);

        startFragment(mFragment, QuestsActivityMock.class);

        verify(mViewModel).setAdapter(isA(AbstractAdapter.class));
        verify(mViewModel).getPage();
        verify(mViewModel).getContainerId();

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
    public void openQuestUi() {
        // arrange
        Quest model = instantiateModel(503L);

        // act
        getFragment().openUi(model);

        // assert
        Intent intent = ShadowApplication.getInstance().getNextStartedActivity();

        Bundle extras = intent.getExtras();
        assertNotNull(extras);
        assertTrue(extras.containsKey(QuestActivity.EXTRA_QUEST_ID));
        long actual = extras.getLong(QuestActivity.EXTRA_QUEST_ID);
        assertEquals(model.getId(), actual);
    }

    @Test
    public void onRefresh() {
        // act
        mFragment.onRefresh();

        // assert
        verify(mViewModel, times(2)).getContainerId();
        verify(mPresenter, times(2)).refresh(isNull(Long.class));
    }

    @Test
    public void onQuestClick() {
        // arrange
        Quest model = mock(Quest.class);

        // act
        getFragment().onAdapterEntityClick(model);

        // assert
        verify(getPresenter()).click(eq(model));
    }
}