package com.achievers.data;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Evidence;
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

@RunWith(MockitoJUnitRunner.class)
public class EvidencesMockDataSourceTest {

    private AchievementsDataSource mDataSource;

    @Mock
    private GetCallback<Evidence> mGetCallback;
    @Mock
    private LoadCallback<Evidence> mLoadCallback;
    @Mock
    private SaveCallback<Long> mSaveCallback;

    @Captor
    private ArgumentCaptor<Evidence> mSuccessCaptor;
    @Captor
    private ArgumentCaptor<Long> mSuccessSaveCaptor;
    @Captor
    private ArgumentCaptor<List<Evidence>> mSuccessListCaptor;
    @Captor
    private ArgumentCaptor<String> mFailureCaptor;

    @Before
    public void before() {
        GeneratorUtils.initialize(new Random(), new Faker());
        mDataSource = AchievementsMockDataSource.getInstance();
    }

    @Test(expected = NullPointerException.class)
    public void getAchievement_nullCallback_shouldThrow() {
        mDataSource.get(-1, null);
    }

    @Test
    public void getAchievement_nonExisting_shouldReturnFailure() {
        //assertEvidenceDoesNotExist(-1);
    }
}