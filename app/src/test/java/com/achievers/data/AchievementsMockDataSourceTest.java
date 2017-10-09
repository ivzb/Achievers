package com.achievers.data;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.achievements.AchievementsMockDataSource;
import com.achievers.utils.GeneratorUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AchievementsMockDataSourceTest {

    private AchievementsDataSource mDataSource;

    @Mock private GetCallback<Achievement> mGetCallback;
    @Mock private LoadCallback<Achievement> mLoadCallback;
    @Mock private SaveCallback<Long> mSaveCallback;

    @Captor private ArgumentCaptor<Achievement> mSuccessCaptor;
    @Captor private ArgumentCaptor<Long> mSuccessSaveCaptor;
    @Captor private ArgumentCaptor<List<Achievement>> mSuccessListCaptor;
    @Captor private ArgumentCaptor<String> mFailureCaptor;

    @Before
    public void before() {
        GeneratorUtils.initialize(new Random(), new Faker());
        mDataSource = AchievementsMockDataSource.getInstance();
    }

    @Test(expected = NullPointerException.class)
    public void getAchievement_nullCallback_shouldThrow() {
        mDataSource.getAchievement(-1, null);
    }

    @Test
    public void getAchievement_nonExisting_shouldReturnFailure() {
        assertAchievementDoesNotExist(-1);
    }

    @Test(expected = NullPointerException.class)
    public void loadAchievements_nullCallback_shouldThrow() {
        mDataSource.loadAchievements(1, null);
    }

    @Test
    public void loadAchievements_firstPage_shouldReturnSuccess() {
        int page = 0;
        int expectedSize = 9;

        mDataSource.loadAchievements(page, mLoadCallback);
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

        mDataSource.loadAchievements(page, mLoadCallback);
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

        mDataSource.loadAchievements(page, mLoadCallback);
        verify(mLoadCallback).onFailure(mFailureCaptor.capture());

        final String actual = mFailureCaptor.getValue();
        final String expected = "Please provide non negative page.";

        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void saveAchievement_nullCallback_shouldThrow() {
        mDataSource.saveAchievement(new Achievement(), null);
    }

    @Test
    public void saveAchievement_valid_shouldReturnSuccess() {
        Achievement expected = new Achievement();

        mDataSource.saveAchievement(expected, mSaveCallback);
        verify(mSaveCallback).onSuccess(mSuccessSaveCaptor.capture());

        final Long actual = mSuccessSaveCaptor.getValue();
        assertNotNull(actual);

        assertAchievementExists(actual);
    }

    @Test
    public void saveAchievement_null_shouldReturnFailure() {
        mDataSource.saveAchievement(null, mSaveCallback);
        verify(mSaveCallback).onFailure(mFailureCaptor.capture());

        final String actual = mFailureCaptor.getValue();
        final String expected = "No achievement to save.";

        assertEquals(expected, actual);
    }

    private void assertAchievementDoesNotExist(long id) {
        mDataSource.getAchievement(id, mGetCallback);

        verify(mGetCallback).onFailure(mFailureCaptor.capture());

        final String actual = mFailureCaptor.getValue();
        final String expected = "Achievement does not exist.";

        assertEquals(expected, actual);
    }

    private void assertAchievementExists(long id) {
        mDataSource.getAchievement(id, mGetCallback);

        verify(mGetCallback).onSuccess(mSuccessCaptor.capture());

        final Achievement actual = mSuccessCaptor.getValue();

        assertEquals(id, actual.getId());
    }
}