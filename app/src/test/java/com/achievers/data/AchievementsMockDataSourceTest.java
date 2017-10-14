package com.achievers.data;

import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsMockDataSource;
import com.achievers.utils.GeneratorUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AchievementsMockDataSourceTest extends BaseMockDataSourceTest<Achievement> {

    @Before
    public void before() {
        GeneratorUtils.initialize(new Random(), new Faker());
        mDataSource = AchievementsMockDataSource.getInstance();
    }

    @Test
    public void loadAchievements_firstPage_shouldReturnSuccess() {
        int page = 0;
        int expectedSize = 9;

        mDataSource.load(null, page, mLoadCallback);
        verify(mLoadCallback).onSuccess(mSuccessListCaptor.capture());

        List<Achievement> actual = mSuccessListCaptor.getValue();

        for (int i = 0; i < expectedSize; i++) {
            int expectedId = i + 1;
            assertEquals(expectedId, actual.get(i).getId());
        }
    }

    @Test
    public void loadAchievements_thirdPage_shouldReturnSuccess() {
        int page = 2;
        int expectedSize = 9;
        int start = page * expectedSize;

        mDataSource.load(null, page, mLoadCallback);
        verify(mLoadCallback).onSuccess(mSuccessListCaptor.capture());

        List<Achievement> actual = mSuccessListCaptor.getValue();

        for (int i = 0; i < expectedSize; i++) {
            int expectedId = start + i + 1;
            assertEquals(expectedId, actual.get(i).getId());
        }
    }

    @Test
    public void loadAchievements_invalidPage_shouldReturnFailure() {
        int page = -1;

        mDataSource.load(null, page, mLoadCallback);
        verify(mLoadCallback).onFailure(mFailureCaptor.capture());

        final String actual = mFailureCaptor.getValue();
        final String expected = "Please provide non negative page.";

        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void saveAchievement_nullCallback_shouldThrow() {
        assertSaveEntityFailure(new Achievement(), null);
    }

    @Test
    public void saveAchievement_valid_shouldReturnSuccess() {
        assertSaveEntitySuccessful(new Achievement());
    }

    @Test
    public void saveAchievement_null_shouldReturnFailure() {
        assertSaveEntityFailure(null, mSaveCallback);
    }
}