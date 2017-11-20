package com.achievers.data;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Involvement;
import com.achievers.data.source.involvements.InvolvementsDataSource;
import com.achievers.data.source.involvements.InvolvementsMockDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InvolvementsMockDataSourceTest {

    private InvolvementsDataSource mDataSource;

    @Mock private LoadCallback<Involvement> mLoadCallback;

    @Captor private ArgumentCaptor<List<Involvement>> mSuccessCaptor;

    @Before
    public void before() {
        mDataSource = InvolvementsMockDataSource.getInstance();
    }

    @Test(expected = NullPointerException.class)
    public void loadInvolvements_nullCallback_shouldThrow() {
        mDataSource.loadInvolvements(null);
    }

    @Test
    public void loadInvolvements_successful() {
        mDataSource.loadInvolvements(mLoadCallback);

        verify(mLoadCallback).onSuccess(mSuccessCaptor.capture());

        final List<Involvement> actual = mSuccessCaptor.getValue();
        final List<Involvement> expected = Arrays.asList(Involvement.values());

        assertEquals(expected, actual);
    }
}