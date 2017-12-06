package com.achievers.data._base;

import com.achievers.data._base.contracts.SeedDataSourceTest;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.sources._base.contracts.ReceiveDataSource;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public abstract class ReceiveDataSourceTest<T extends BaseModel>
        extends GetDataSourceTest<T>
        implements SeedDataSourceTest {

    protected ReceiveDataSource<T> mDataSource;

    @Mock protected LoadCallback<T> mLoadCallback;

    @Captor protected ArgumentCaptor<List<T>> mSuccessListCaptor;

    @Test(expected = NullPointerException.class)
    public void load_nullCallback_shouldThrow() {
        mDataSource.load(null, 1, null);
    }

    @Test
    public void load_invalidPage_shouldReturnFailure() {
        Long id = 5L;
        int page = -1;

        mDataSource.load(id, page, mLoadCallback);
        verify(mLoadCallback).onFailure(mFailureCaptor.capture());

        final String actual = mFailureCaptor.getValue();
        final String expected = "Please provide non negative page.";

        assertEquals(expected, actual);
    }

    @Test
    public void load_assertNoMore() {
        long containerId = 5;
        int page = 0;

        load_assertNoMore(containerId, page);
    }

    @Test
    public void load_firstPage_assertSuccess() {
        long containerId = 5;
        int page = 0;
        int expectedSize = 9;

        seed(containerId, (page + 1) * expectedSize);

        load_assertSuccess(containerId, page, expectedSize);
    }

    @Test
    public void load_thirdPage_assertSuccess() {
        long containerId = 5;
        int page = 2;
        int expectedSize = 9;

        seed(containerId, (page + 1) * expectedSize);

        load_assertSuccess(containerId, page, expectedSize);
    }

    protected void load_assertSuccess(Long id, int page, int expectedSize) {
        mDataSource.load(id, page, mLoadCallback);
        verify(mLoadCallback).onSuccess(mSuccessListCaptor.capture(), eq(page));

        List<T> actual = mSuccessListCaptor.getValue();
        assertEquals(expectedSize, actual.size());
    }

    protected void load_assertNoMore(Long id, int page) {
        mDataSource.load(id, page, mLoadCallback);
        verify(mLoadCallback).onNoMore();
    }

    protected void setDataSource(ReceiveDataSource<T> dataSource) {
        super.setDataSource(dataSource);
        mDataSource = dataSource;
    }
}