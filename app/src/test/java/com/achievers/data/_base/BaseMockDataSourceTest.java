package com.achievers.data._base;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.sources._base.contracts.BaseDataSource;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

public abstract class BaseMockDataSourceTest<T extends BaseModel>
        extends ReceiveDataSourceTest<T> {

    protected BaseDataSource<T> mDataSource;

    @Mock protected SaveCallback<String> mSaveCallback;

    @Captor protected ArgumentCaptor<String> mSuccessSaveCaptor;

    @Test
    public void save_null_shouldReturnFailure() {
        assertSaveEntityFailure(null, mSaveCallback);
    }

    protected void assertSaveEntitySuccessful(T entity) {
        mDataSource.save(entity, mSaveCallback);
        verify(mSaveCallback).onSuccess(mSuccessSaveCaptor.capture());

        final String actual = mSuccessSaveCaptor.getValue();
        assertNotNull(actual);

        assertEntityExists(actual);
    }

    protected void assertSaveEntityFailure(T entity, SaveCallback<String> callback) {
        mDataSource.save(entity, callback);
        verify(mSaveCallback).onFailure(mFailureCaptor.capture());

        final String actual = mFailureCaptor.getValue();
        final String expected = "No entity to save.";

        assertEquals(expected, actual);
    }

    protected void setDataSource(BaseDataSource<T> dataSource) {
        super.setDataSource(dataSource);
        mDataSource = dataSource;
    }
}