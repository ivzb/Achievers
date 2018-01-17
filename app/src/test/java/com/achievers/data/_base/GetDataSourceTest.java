package com.achievers.data._base;

import com.achievers.data.Result;
import com.achievers.data._base.contracts.SeedDataSourceTest;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.sources._base.contracts.GetDataSource;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

public abstract class GetDataSourceTest<T extends BaseModel>
        implements SeedDataSourceTest<T> {

    protected GetDataSource<T> mDataSource;

    @Mock protected GetCallback<T> mGetCallback;

    @Captor protected ArgumentCaptor<Result<T>> mSuccessCaptor;
    @Captor protected ArgumentCaptor<String> mFailureCaptor;

    @Test(expected = NullPointerException.class)
    public void get_nullCallback_shouldThrow() {
        mDataSource.get("some_id", null);
    }

    @Test
    public void get_nonExisting_shouldReturnFailure() {
        assertEntityDoesNotExist("not_existing_id");
    }

    @Test
    public void get_existing_shouldReturnCorrect() {
        List<T> existing = seed(null, 10);
        String expectedId = existing.get(0).getId();

        mDataSource.get(expectedId, mGetCallback);
        verify(mGetCallback).onSuccess(mSuccessCaptor.capture());

        Result<T> actual = mSuccessCaptor.getValue();
        assertNotNull(actual);
        assertNotNull(actual.getResults());
        assertEquals(expectedId, actual.getResults().getId());
    }

    protected void assertEntityDoesNotExist(String id) {
        mDataSource.get(id, mGetCallback);

        verify(mGetCallback).onFailure(mFailureCaptor.capture());

        final String actual = mFailureCaptor.getValue();
        final String expected = "Entity does not exist.";

        assertEquals(expected, actual);
    }

    protected void assertEntityExists(String id) {
        mDataSource.get(id, mGetCallback);

        verify(mGetCallback).onSuccess(mSuccessCaptor.capture());

        final Result<T> actual = mSuccessCaptor.getValue();

        assertEquals(id, actual.getResults().getId());
    }

    protected void setDataSource(GetDataSource<T> dataSource) {
        mDataSource = dataSource;
    }
}