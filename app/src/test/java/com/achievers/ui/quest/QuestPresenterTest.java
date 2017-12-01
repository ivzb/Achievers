package com.achievers.ui.quest;

import android.content.Context;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources.achievements.AchievementsDataSource;
import com.achievers.ui._base.EndlessAdapterPresenterTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class QuestPresenterTest
        extends EndlessAdapterPresenterTest<Achievement, QuestContract.Presenter, QuestContract.View, AchievementsDataSource> {

    @Mock
    private Context mContext;
    @Mock private QuestContract.View mView;
    @Mock private AchievementsDataSource mDataSource;

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public QuestContract.View getView() {
        return mView;
    }

    @Override
    public AchievementsDataSource getDataSource() {
        return mDataSource;
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(new Random(), new Faker());

        mPresenter = new QuestPresenter(mContext, mView, mDataSource);
        mId = 5L;
    }

    @Override
    public Achievement instantiateModel(Long id) {
        if (id == null) return new Achievement();
        return new Achievement(id);
    }

    @Override
    public QuestContract.Presenter instantiatePresenter(
            Context context,
            QuestContract.View view,
            AchievementsDataSource dataSource) {

        return new QuestPresenter(context, view, dataSource);
    }

    @Test
    @Override
    public void load_callbackInactiveView() {
        int page = 0;

        arrangeLoad(
                mId,
                true,
                true,
                false,
                page);

        actLoad(mId, page);

        // assert
        verify(getView()).setLoadingIndicator(true);
        verify(getDataSource()).loadByQuestId(eq(mId), eq(page), any(LoadCallback.class));
        verify(getView(), times(2)).isActive();
        verify(getView()).setMore(anyBoolean());
        verifyNoMoreInteractions(getView());
    }

    @Override
    protected void arrangeLoad(
            final Long id,
            final Boolean isSuccessful,
            final Boolean initiallyInactiveView,
            final Boolean callbackInactiveView,
            final int page) {

        mLoadCaptor = ArgumentCaptor.forClass(List.class);

        when(getView().isActive()).thenReturn(initiallyInactiveView);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                LoadCallback<Achievement> callback =
                        (LoadCallback<Achievement>) invocation.getArguments()[2];

                when(getView().isActive()).thenReturn(callbackInactiveView);

                if (isSuccessful) {
                    mExpectedLoad = generate(page);
                    callback.onSuccess(mExpectedLoad, page);
                    return null;
                }

                callback.onFailure(sLoadFailure);
                return null;
            }
        }).when(getDataSource()).loadByQuestId(
                id == null ? isNull(Long.class) : any(Long.class), any(int.class), any(LoadCallback.class));
    }

    @Override
    protected void assertSuccessfulLoad(Long id, int page) {
        verify(getView()).setLoadingIndicator(true);

        verify(getDataSource()).loadByQuestId(eq(id), eq(page), any(LoadCallback.class));
        verify(getView()).show(mLoadCaptor.capture());
        verify(getView()).setLoadingIndicator(false);
        verify(getView()).setPage(any(int.class));
        verify(getView(), times(2)).isActive();
        verifyNoMoreInteractions(getView());

        List<Achievement> actualLoad = mLoadCaptor.getValue();
        assertTrue(mExpectedLoad == actualLoad);
    }

    @Override
    protected void assertFailureLoad(Long id, int page) {
        verify(getView()).setLoadingIndicator(true);

        verify(getDataSource()).loadByQuestId(eq(id), eq(page), any(LoadCallback.class));
        verify(getView()).showErrorMessage(any(String.class));
        verify(getView()).setLoadingIndicator(false);
        verify(getView(), times(2)).setMore(anyBoolean());
        verify(getView(), times(2)).isActive();
        verifyNoMoreInteractions(getView());
    }
}