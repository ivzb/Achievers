package com.achievers.ui.categories;

import android.database.Cursor;
import android.os.Bundle;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCategoriesPresenter {

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
                mDataSource,
                mView);
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
