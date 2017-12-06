package com.achievers.data._base;

import com.achievers.data._base.contracts.SeedDataSourceTest;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.sources._base.contracts.GetDataSource;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

public abstract class GetDataSourceTest<T extends BaseModel>
        implements SeedDataSourceTest {

    protected GetDataSource<T> mDataSource;

    @Mock protected GetCallback<T> mGetCallback;

    @Captor protected ArgumentCaptor<T> mSuccessCaptor;
    @Captor protected ArgumentCaptor<String> mFailureCaptor;

    @Test(expected = NullPointerException.class)
    public void get_nullCallback_shouldThrow() {
        mDataSource.get(-1, null);
    }

    @Test
    public void get_nonExisting_shouldReturnFailure() {
        assertEntityDoesNotExist(-1);
    }

    @Test
    public void get_existing_shouldReturnCorrect() {
        seed(null, 10);
        long expectedId = 5L;

        mDataSource.get(expectedId, mGetCallback);
        verify(mGetCallback).onSuccess(mSuccessCaptor.capture());

        T actual = mSuccessCaptor.getValue();
        assertNotNull(actual);
        assertEquals(expectedId, actual.getId());
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

    protected void setDataSource(GetDataSource<T> dataSource) {
        mDataSource = dataSource;
    }
}