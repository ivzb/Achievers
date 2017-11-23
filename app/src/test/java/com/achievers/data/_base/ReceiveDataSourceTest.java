package com.achievers.data._base;

import com.achievers.data.callbacks.GetCallback;
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

public abstract class ReceiveDataSourceTest<T extends BaseModel> {

    protected ReceiveDataSource<T> mDataSource;

    @Mock protected GetCallback<T> mGetCallback;
    @Mock protected LoadCallback<T> mLoadCallback;

    @Captor protected ArgumentCaptor<T> mSuccessCaptor;
    @Captor protected ArgumentCaptor<List<T>> mSuccessListCaptor;
    @Captor protected ArgumentCaptor<String> mFailureCaptor;

    @Test(expected = NullPointerException.class)
    public void get_nullCallback_shouldThrow() {
        mDataSource.get(-1, null);
    }

    @Test
    public void get_nonExisting_shouldReturnFailure() {
        assertEntityDoesNotExist(-1);
    }

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
    public void load_firstPage_assertSuccess() {
        long containerId = 5;
        int page = 0;
        int expectedSize = 9;

        load_assertSuccess(containerId, page, expectedSize);
    }

    @Test
    public void load_thirdPage_assertSuccess() {
        long containerId = 5;
        int page = 2;
        int expectedSize = 9;

        load_assertSuccess(containerId, page, expectedSize);
    }

    protected void assertEntityDoesNotExist(long id) {
        mDataSource.get(id, mGetCallback);

        verify(mGetCallback).onFailure(mFailureCaptor.capture());

        final String actual = mFailureCaptor.getValue();
        final String expected = "Entity does not exist.";

        assertEquals(expected, actual);
    }

    protected void assertEntityExists(long id) {
        mDataSource.get(id, mGetCallback);

        verify(mGetCallback).onSuccess(mSuccessCaptor.capture());

        final T actual = mSuccessCaptor.getValue();

        assertEquals(id, actual.getId());
    }

    protected void load_assertSuccess(Long id, int page, int expectedSize) {
        mDataSource.load(id, page, mLoadCallback);
        verify(mLoadCallback).onSuccess(mSuccessListCaptor.capture(), eq(page));

        List<T> actual = mSuccessListCaptor.getValue();
        assertEquals(expectedSize, actual.size());
    }

    protected void setDataSource(ReceiveDataSource<T> dataSource) {
        mDataSource = dataSource;
    }
}