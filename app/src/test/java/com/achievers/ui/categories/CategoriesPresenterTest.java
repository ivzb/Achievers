package com.achievers.ui.categories;

import com.achievers.data.source.categories.CategoriesRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CategoriesPresenterTest {

//    @Mock
//    private LoaderManager mLoaderManager;

//    @Mock
//    private CategoriesLoaderProvider mLoaderProvider;

    @Mock
    private CategoriesRepository mDataSource;

    @Mock
    private CategoriesFragment mView;

    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();

//    @Captor
//    private ArgumentCaptor<LoadCallback<Category>> mLoadCategoriesCallbackCaptor;

    private CategoriesPresenter mPresenter;

    @Before
    public void setUp() {
        mPresenter = new CategoriesPresenter(
//                mLoaderManager,
//                mLoaderProvider,
                mView,
                mDataSource);
    }

    @Test
    public void loadCategoriesFromRepository_showCategoriesInView() {
        mPresenter.loadCategories(null);

        verify(mView).setLoadingIndicator(true);
        verify(mDataSource).load(nullable(Long.class));//, mLoadCategoriesCallbackCaptor.capture());

//        List<Category> testData = new ArrayList<>();
//        testData.add(new Category());
//        testData.add(new Category());
//        testData.add(new Category());
//
//        mLoadCategoriesCallbackCaptor.getValue().onSuccess(testData);

//        verify(mView).showCategories(any(Cursor.class));
    }
}
