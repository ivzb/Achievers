package com.achievers.data.sources.files;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FilesMockDataSourceTest {

    private FilesDataSource mDataSource;

    @Mock private File mFile;
    @Mock private SaveCallback<String> mSaveCallback;

    @Captor private ArgumentCaptor<String> mSuccessCaptor;
    @Captor private ArgumentCaptor<String> mFailureCaptor;

    @Before
    public void before() {
        FilesMockDataSource.destroyInstance();
        mDataSource = FilesMockDataSource.createInstance();
    }

    @Test(expected = NullPointerException.class)
    public void storeFile_nullCallback_shouldThrow() {
        mDataSource.storeFile(mFile, null);
    }

    @Test
    public void storeFile_nullFile_failureCallback() {
        mDataSource.storeFile(null, mSaveCallback);

        verify(mSaveCallback).onFailure(mFailureCaptor.capture());

        final String actual = mFailureCaptor.getValue();
        final String expected = "No file to save.";

        assertEquals(expected, actual);
    }

    @Test
    public void storeFile_successfulCallback() {
        mDataSource.storeFile(mFile, mSaveCallback);

        verify(mFile).setId(anyString());
        verify(mSaveCallback).onSuccess(mSuccessCaptor.capture());

        final String actual = mSuccessCaptor.getValue();
        final String expected = "mock_url";

        assertEquals(expected, actual);
    }
}