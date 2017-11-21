package com.achievers.ui._base;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.source._base.contracts.ReceiveDataSource;
import com.achievers.ui._base._contracts.BaseAdapterPresenterTest;
import com.achievers.ui._base._contracts.presenters.BaseEndlessAdapterPresenter;
import com.achievers.ui._base._contracts.views.BaseEndlessAdapterView;

import org.junit.After;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public abstract class EndlessAdapterPresenterTest<M extends BaseModel, P extends BaseEndlessAdapterPresenter<M>, V extends BaseEndlessAdapterView, DS extends ReceiveDataSource<M>>
        implements BaseAdapterPresenterTest<M, P, V, DS> {

    @Captor private ArgumentCaptor<List<M>> mLoadCaptor;

    protected P mPresenter;

    private List<M> mExpectedLoad;

    private static final String sLoadFailure = "Could not load entities";

    @After
    public void after() {
        verifyNoMoreInteractions(getContext());
        verifyNoMoreInteractions(getView());
        verifyNoMoreInteractions(getDataSource());
    }

    @Test(expected = NullPointerException.class)
    public void nullContext_shouldThrow() {
        instantiatePresenter(null, getView(), getDataSource());
    }

    @Test(expected = NullPointerException.class)
    public void nullView_shouldThrow() {
        instantiatePresenter(getContext(), null, getDataSource());
    }

    @Test(expected = NullPointerException.class)
    public void nullDataSource_shouldThrow() {
        instantiatePresenter(getContext(), getView(), null);
    }

    @Test
    public void start_shouldDoNothing() {
        mPresenter.start();
        verifyNoMoreInteractions(getView());
    }

    @Test
    public void load_firstPage_successful() {
        int page = 0;

        arrangeLoad(
                true,
                true,
                true,
                page);

        actLoad(page);
        assertSuccessfulLoad(page);
    }

    @Test
    public void load_firstPage_failure() {
        int page = 0;

        arrangeLoad(
                false,
                true,
                true,
                page);

        actLoad(page);
        assertFailureLoad(page);
    }

    @Test
    public void load_thirdPage_successful() {
        int page = 9;

        arrangeLoad(
                true,
                true,
                true,
                page);

        actLoad(page);
        assertSuccessfulLoad(page);
    }

    @Test
    public void load_thirdPage_failure() {
        int page = 9;

        arrangeLoad(
                false,
                true,
                true,
                page);

        actLoad(page);
        assertFailureLoad(page);
    }

    @Test
    public void load_initiallyInactiveView() {
        int page = 0;

        arrangeLoad(
                null,
                false,
                null,
                page);

        actLoad(page);

        // assert
        verify(getView()).isActive();
        verifyNoMoreInteractions(getView());
    }

    @Test
    public void load_callbackInactiveView() {
        int page = 0;

        arrangeLoad(
                true,
                true,
                false,
                page);

        actLoad(page);

        // assert
        verify(getView()).setLoadingIndicator(true);
        verify(getDataSource()).load(isNull(Long.class), eq(page), any(LoadCallback.class));
        verify(getView(), times(2)).isActive();
        verifyNoMoreInteractions(getView());
    }


    @Test
    public void click_shouldOpenUi() {
        // arrange
        when(getView().isActive()).thenReturn(true);
        M entity = instantiateModel(null);

        // act
        mPresenter.click(entity);

        // assert
        verify(getView()).isActive();
        verify(getView()).openUi(entity);
        verifyNoMoreInteractions(getView());
    }

    @Test
    public void click_withoutModel_shouldShowErrorMessage() {
        // arrange
        when(getView().isActive()).thenReturn(true);

        // act
        mPresenter.click(null);

        // assert
        verify(getView()).isActive();
        verify(getView()).showErrorMessage(any(String.class));
        verifyNoMoreInteractions(getView());
    }

    @Test
    public void click_inactiveView_shouldReturn() {
        // arrange
        when(getView().isActive()).thenReturn(false);

        // act
        mPresenter.click(null);

        // assert
        verify(getView()).isActive();
        verifyNoMoreInteractions(getView());
    }

    private void arrangeLoad(
            final Boolean isSuccessful,
            final Boolean initiallyInactiveView,
            final Boolean callbackInactiveView,
            final int page) {

        mLoadCaptor = ArgumentCaptor.forClass(List.class);

        when(getView().isActive()).thenReturn(initiallyInactiveView);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                LoadCallback<M> callback =
                        (LoadCallback<M>) invocation.getArguments()[2];

                when(getView().isActive()).thenReturn(callbackInactiveView);

                if (isSuccessful) {
                    mExpectedLoad = generate(page);
                    callback.onSuccess(mExpectedLoad, page);
                    return null;
                }

                callback.onFailure(sLoadFailure);
                return null;
            }
        }).when(getDataSource()).load(
                isNull(Long.class), any(int.class), any(LoadCallback.class));
    }

    private void actLoad(int page) {
        mPresenter.load(null, page);
    }

    private void assertSuccessfulLoad(int page) {
        verify(getView()).setLoadingIndicator(true);

        verify(getDataSource()).load(isNull(Long.class), eq(page), any(LoadCallback.class));
        verify(getView()).show(mLoadCaptor.capture());
        verify(getView()).setLoadingIndicator(false);
        verify(getView()).setPage(any(int.class));
        verify(getView(), times(2)).isActive();
        verifyNoMoreInteractions(getView());

        List<M> actualLoad = mLoadCaptor.getValue();
        assertTrue(mExpectedLoad == actualLoad);
    }

    private void assertFailureLoad(int page) {
        verify(getView()).setLoadingIndicator(true);

        verify(getDataSource()).load(isNull(Long.class), eq(page), any(LoadCallback.class));
        verify(getView()).showErrorMessage(any(String.class));
        verify(getView()).setLoadingIndicator(false);
        verify(getView(), times(2)).isActive();
        verifyNoMoreInteractions(getView());
    }

    private List<M> generate(int page) {
        List<M> entities = new ArrayList<>();
        int end = 9 * page;

        for (long id = 0; id < end; id++) {
            M entity = instantiateModel(id);
            entities.add(entity);
        }

        return entities;
    }
}