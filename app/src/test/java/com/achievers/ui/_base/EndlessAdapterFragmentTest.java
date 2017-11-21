package com.achievers.ui._base;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.ui._base._contracts.BaseAdapterFragmentTest;
import com.achievers.ui._base._contracts.presenters.BaseEndlessAdapterPresenter;
import com.achievers.ui._base._contracts.view_models.BaseEndlessAdapterViewModel;
import com.achievers.ui._base._contracts.views.BaseEndlessAdapterView;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public abstract class EndlessAdapterFragmentTest<M extends BaseModel, F extends BaseEndlessAdapterView, P extends BaseEndlessAdapterPresenter, VM extends BaseEndlessAdapterViewModel>
        implements BaseAdapterFragmentTest<M, F, P, VM> {

    private Class<M> mModelClass;

    public EndlessAdapterFragmentTest(Class<M> modelClass) {
        mModelClass = modelClass;
    }

    @After
    public void after() {
        verifyNoMoreInteractions(getViewModel());
        verifyNoMoreInteractions(getPresenter());
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(getFragment());
    }

    @Test
    public void showAchievements() {
        // arrange
        List<M> achievements = new ArrayList<>();
        for (long i = 0; i < 5; i++) achievements.add(instantiateModel(i));

        AbstractAdapter<M> adapter = mock(AbstractAdapter.class);
        when(getViewModel().getAdapter()).thenReturn(adapter);

        // act
        getFragment().show(achievements);

        // assert
        verify(getViewModel()).getAdapter();
        verify(adapter).add(eq(achievements));
    }

    @Test
    public void getPage() {
        // act
        getFragment().getPage();

        // assert
        verify(getViewModel(), times(2)).getPage();
    }

    @Test
    public void setPage() {
        // arrange
        int page = 5;

        // act
        getFragment().setPage(page);

        // assert
        verify(getViewModel()).setPage(eq(5));
    }
}