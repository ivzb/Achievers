package com.achievers.ui.categories;

import android.support.v4.app.LoaderManager;
import android.test.suitebuilder.annotation.SmallTest;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.source.categories.CategoriesLoaderProvider;
import com.achievers.data.source.categories.CategoriesRepository;
import com.achievers.data.source.categories.remote.CategoriesRemoteDataSource;
import com.achievers.entities.Category;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SmallTest
public class TestCategoriesPresenter {

    @Mock
    private LoaderManager mLoaderManager;

    @Mock
    private CategoriesLoaderProvider mLoaderProvider;

    @Mock
    private CategoriesRepository mDataSource;

    @Mock
    private CategoriesFragment mView;

    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();

    private CategoriesContract.Presenter mPresenter;

    @Before
    public void setUp() {
        mPresenter = new CategoriesPresenter(
                mLoaderManager,
                mLoaderProvider,
                mDataSource,
                mView);
    }

    @Test
    public void start_showCategories() {
        mPresenter.start();

        verify(mView).setLoadingIndicator(true);
        verify(mPresenter).loadCategories();
    }
}
