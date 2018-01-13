package com.achievers.data;

import com.achievers.data.sources.DataSources;
import com.achievers.data.sources.MockStrategy;
import com.achievers.data.sources.RemoteStrategy;
import com.achievers.data.sources.achievements.AchievementsMockDataSource;
import com.achievers.data.sources.achievements.AchievementsRemoteDataSource;
import com.achievers.data.sources.authentication.AuthenticationMockDataSource;
import com.achievers.data.sources.authentication.AuthenticationRemoteDataSource;
import com.achievers.data.sources.contributions.ContributionsMockDataSource;
import com.achievers.data.sources.evidences.EvidencesMockDataSource;
import com.achievers.data.sources.evidences.EvidencesRemoteDataSource;
import com.achievers.data.sources.files.FilesMockDataSource;
import com.achievers.data.sources.files.FilesRemoteDataSource;
import com.achievers.data.sources.involvements.InvolvementsMockDataSource;
import com.achievers.data.sources.profiles.ProfilesMockDataSource;
import com.achievers.data.sources.quests.QuestsMockDataSource;
import com.achievers.data.sources.rewards.RewardsMockDataSource;
import com.achievers.data.sources.user.UserPreferencesDataSource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DataSourcesTests {

    private static String sFailMessage = "DataSource strategy entity does not match";

    @After
    public void after() {
        DataSources.destroyInstance();
    }

    @Test
    public void testMockStrategy() {
        // arrange
        DataSources.createInstance(new MockStrategy());
        Class expectedAchievements = AchievementsMockDataSource.class;
        Class expectedAuthentication = AuthenticationMockDataSource.class;
        Class expectedContributions = ContributionsMockDataSource.class;
        Class expectedEvidences = EvidencesMockDataSource.class;
        Class expectedFiles = FilesMockDataSource.class;
        Class expectedInvolvements = InvolvementsMockDataSource.class;
        Class expectedProfiles = ProfilesMockDataSource.class;
        Class expectedQuests = QuestsMockDataSource.class;
        Class expectedRewards = RewardsMockDataSource.class;
        Class expectedUser = UserPreferencesDataSource.class;

        // act
        Class actualAchievements = DataSources.getInstance().getAchievements().getClass();
        Class actualAuthentication = DataSources.getInstance().getAuthentication().getClass();
        Class actualContributions = DataSources.getInstance().getContributions().getClass();
        Class actualEvidences = DataSources.getInstance().getEvidences().getClass();
        Class actualFiles = DataSources.getInstance().getFiles().getClass();
        Class actualInvolvements = DataSources.getInstance().getInvolvements().getClass();
        Class actualProfiles = DataSources.getInstance().getProfiles().getClass();
        Class actualQuests = DataSources.getInstance().getQuests().getClass();
        Class actualRewards = DataSources.getInstance().getRewards().getClass();
        Class actualUser = DataSources.getInstance().getUser().getClass();

        // assert
        assertEquals(sFailMessage, expectedAchievements.getSimpleName(), actualAchievements.getSimpleName());
        assertEquals(sFailMessage, expectedAuthentication.getSimpleName(), actualAuthentication.getSimpleName());
        assertEquals(sFailMessage, expectedContributions.getSimpleName(), actualContributions.getSimpleName());
        assertEquals(sFailMessage, expectedEvidences.getSimpleName(), actualEvidences.getSimpleName());
        assertEquals(sFailMessage, expectedFiles.getSimpleName(), actualFiles.getSimpleName());
        assertEquals(sFailMessage, expectedInvolvements.getSimpleName(), actualInvolvements.getSimpleName());
        assertEquals(sFailMessage, expectedProfiles.getSimpleName(), actualProfiles.getSimpleName());
        assertEquals(sFailMessage, expectedQuests.getSimpleName(), actualQuests.getSimpleName());
        assertEquals(sFailMessage, expectedRewards.getSimpleName(), actualRewards.getSimpleName());
        assertEquals(sFailMessage, expectedUser.getSimpleName(), actualUser.getSimpleName());
    }

    @Test
    public void testRemoteStrategy() {
        // arrange
        DataSources.createInstance(new RemoteStrategy());
        Class expectedAchievements = AchievementsRemoteDataSource.class;
        Class expectedAuthentication = AuthenticationRemoteDataSource.class;
        // todo: add contributions
        Class expectedEvidences = EvidencesRemoteDataSource.class;
        Class expectedFiles = FilesRemoteDataSource.class;
        // todo: add involvements
        // todo: add profiles
        // todo: add quests
        // todo: add rewards
        Class expectedUser = UserPreferencesDataSource.class;

        // act
        Class actualAchievements = DataSources.getInstance().getAchievements().getClass();
        Class actualAuthentication = DataSources.getInstance().getAuthentication().getClass();
        // todo: add contributions
        Class actualEvidences = DataSources.getInstance().getEvidences().getClass();
        Class actualFiles = DataSources.getInstance().getFiles().getClass();
        // todo: add involvements
        // todo: add profiles
        // todo: add quests
        // todo: add rewards
        Class actualUser = DataSources.getInstance().getUser().getClass();

        // assert
        assertEquals(sFailMessage, expectedAchievements.getSimpleName(), actualAchievements.getSimpleName());
        assertEquals(sFailMessage, expectedAuthentication.getSimpleName(), actualAuthentication.getSimpleName());
        // todo: add contributions
        assertEquals(sFailMessage, expectedEvidences.getSimpleName(), actualEvidences.getSimpleName());
        assertEquals(sFailMessage, expectedFiles.getSimpleName(), actualFiles.getSimpleName());
        // todo: add involvements
        // todo: add profiles
        // todo: add quests
        // todo: add rewards
        assertEquals(sFailMessage, expectedUser.getSimpleName(), actualUser.getSimpleName());
    }
}
