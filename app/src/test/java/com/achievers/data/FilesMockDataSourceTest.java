package com.achievers.data;

import android.net.Uri;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.File;
import com.achievers.data.source.files.FilesDataSource;
import com.achievers.data.source.files.FilesMockDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FilesMockDataSourceTest {

    private FilesDataSource mDataSource;

    @Mock private File mFile;
    @Mock private SaveCallback<Uri> mSaveCallback;
    @Mock private Uri mUri;

    @Captor private ArgumentCaptor<Uri> mSuccessCaptor;
    @Captor private ArgumentCaptor<String> mFailureCaptor;

    @Before
    public void before() {
        mDataSource = FilesMockDataSource.getInstance();
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
        when(mFile.getUri()).thenReturn(mUri);

        mDataSource.storeFile(mFile, mSaveCallback);

        verify(mFile).setId(anyLong());
        verify(mSaveCallback).onSuccess(mSuccessCaptor.capture());

        final Uri actual = mSuccessCaptor.getValue();
        final Uri expected = mUri;

        assertEquals(expected, actual);
    }
}