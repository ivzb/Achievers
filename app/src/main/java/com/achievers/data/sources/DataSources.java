package com.achievers.data.sources;

import com.achievers.data.sources.achievements.AchievementsDataSource;
import com.achievers.data.sources.authentication.AuthenticationDataSource;
import com.achievers.data.sources.contributions.ContributionsDataSource;
import com.achievers.data.sources.evidences.EvidencesDataSource;
import com.achievers.data.sources.files.FilesDataSource;
import com.achievers.data.sources.involvements.InvolvementsDataSource;
import com.achievers.data.sources.profiles.ProfilesDataSource;
import com.achievers.data.sources.quests.QuestsDataSource;
import com.achievers.data.sources.rewards.RewardsDataSource;
import com.achievers.data.sources.user.UserDataSource;
import com.achievers.data.sources.user.UserPreferencesDataSource;

import static com.achievers.utils.Preconditions.checkNotNull;

/**
 *
 * DataSources uses different strategies to return correct data source
 *
 */
public class DataSources implements DataSourcesStrategy {

    private static DataSources sINSTANCE;

    private DataSourcesStrategy mStrategy;

    public static DataSources getInstance() {
        return checkNotNull(sINSTANCE);
    }

    public static void createInstance(DataSourcesStrategy strategy) {
        sINSTANCE = new DataSources(strategy);
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    private DataSources(DataSourcesStrategy strategy) {
        mStrategy = strategy;
    }

    @Override
    public AchievementsDataSource getAchievements() {
        return mStrategy.getAchievements();
    }

    @Override
    public AuthenticationDataSource getAuthentication() {
        return mStrategy.getAuthentication();
    }

    @Override
    public ContributionsDataSource getContributions() {
        return mStrategy.getContributions();
    }

    @Override
    public EvidencesDataSource getEvidences() {
        return mStrategy.getEvidences();
    }

    @Override
    public FilesDataSource getFiles() {
        return mStrategy.getFiles();
    }

    @Override
    public InvolvementsDataSource getInvolvements() {
        return mStrategy.getInvolvements();
    }

    @Override
    public ProfilesDataSource getProfiles() {
        return mStrategy.getProfiles();
    }

    @Override
    public QuestsDataSource getQuests() {
        return mStrategy.getQuests();
    }

    @Override
    public RewardsDataSource getRewards() {
        return mStrategy.getRewards();
    }

    @Override
    public UserDataSource getUser() {
        return UserPreferencesDataSource.getInstance();
    }
}
