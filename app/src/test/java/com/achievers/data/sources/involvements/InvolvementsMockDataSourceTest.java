package com.achievers.data.sources.involvements;

import com.achievers.data.Result;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Involvement;

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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InvolvementsMockDataSourceTest {

    private InvolvementsDataSource mDataSource;

    @Mock private LoadCallback<Involvement> mLoadCallback;

    @Captor private ArgumentCaptor<Result<List<Involvement>>> mSuccessCaptor;

    @Before
    public void before() {
        InvolvementsMockDataSource.destroyInstance();
        mDataSource = InvolvementsMockDataSource.createInstance();
    }

    @Test(expected = NullPointerException.class)
    public void loadInvolvements_nullCallback_shouldThrow() {
        mDataSource.loadInvolvements(null);
    }

    @Test
    public void loadInvolvements_successful() {
        mDataSource.loadInvolvements(mLoadCallback);

        verify(mLoadCallback).onSuccess(mSuccessCaptor.capture(), eq(0));

        Result<List<Involvement>> data = mSuccessCaptor.getValue();
        final List<Involvement> actual = data.getResults();
        final List<Involvement> expected = Arrays.asList(Involvement.values());

        assertEquals(expected, actual);
    }
}